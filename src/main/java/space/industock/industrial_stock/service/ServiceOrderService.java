package space.industock.industrial_stock.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import space.industock.industrial_stock.domain.ServiceOrder;
import space.industock.industrial_stock.domain.ServicePicture;
import space.industock.industrial_stock.domain.User;
import space.industock.industrial_stock.domain.utils.UserDetailsAdapter;
import space.industock.industrial_stock.dto.ServiceOrderDTO;
import space.industock.industrial_stock.dto.routes.LngLat;
import space.industock.industrial_stock.enums.PictureType;
import space.industock.industrial_stock.enums.Stage;
import space.industock.industrial_stock.event.EnqueueClientServiceEvent;
import space.industock.industrial_stock.event.NextQueueServiceEvent;
import space.industock.industrial_stock.exception.InternalAuthorizedException;
import space.industock.industrial_stock.repository.ServiceOrderRepository;
import space.industock.industrial_stock.repository.ServicePictureRepository;
import space.industock.industrial_stock.repository.UserRepository;

import java.io.InputStream;
import java.util.List;

@Service
@Log4j2
public class ServiceOrderService extends BaseService<ServiceOrder, ServiceOrderDTO> {

  private final ServiceOrderRepository repository;
  private final UserRepository userRepository;
  private final ApplicationEventPublisher publisher;
  private final ServicePictureRepository pictureRepository;
  private final ImageStorageService storageService;

  public ServiceOrderService(ServiceOrderRepository repository, UserRepository userRepository, ApplicationEventPublisher publisher, ServicePictureRepository pictureRepository, ImageStorageService storageService) {
    super(repository);
    this.repository = repository;
    this.userRepository = userRepository;
    this.publisher = publisher;
    this.pictureRepository = pictureRepository;
    this.storageService = storageService;
  }

  @Override
  public ServiceOrderDTO save(ServiceOrderDTO dto){
    ServiceOrder toSave = toEntity(dto);
    log.info(toSave.getStage());
    ServiceOrder saved = repository.save(toSave);

    publisher.publishEvent(new EnqueueClientServiceEvent(saved.getClient(), Stage.PENDENTE_PRODUCAO));

    return toDto(saved);
  }

  /* ============================================================
   * FINALIZA PRODUÇÃO
   * ============================================================ */
  public ServiceOrderDTO finishProduction(Long serviceId){
    ServiceOrder service = repository.findById(serviceId).orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

    User user = this.getCurrentUser();
    log.info(service);

    service.setCurrentUser(null);

    publisher.publishEvent(new NextQueueServiceEvent(service));

    return toDto(service);
  }

  /* ============================================================
   * FINALIZA ENTREGA
   * ============================================================ */
  @Transactional
  public ServiceOrderDTO finishDelivered(Long serviceId, LngLat lngLat){
    User user = this.getCurrentUser();
    ServiceOrder service = repository.findById(serviceId).orElseThrow(() -> new InternalAuthorizedException("Serviço não encontrado"));
    service.setDeliveredLocation(lngLat);

    publisher.publishEvent(new NextQueueServiceEvent(service));

    return toDto(service);
  }


//  /**
//   * Cria um serviço e insere o cliente na fila do stage.
//   */
//  @Override
//  public ServiceOrderDTO save(ServiceOrderDTO order) {
//    order.setStatus(QueueStatus.PENDING);
//    repository.save(toEntity(order));
//
//    publisher.publishEvent(new EnqueueClientServiceEvent(order.getClient(), order.getStage()));
//
//    return order;
//  }
//

  public List<ServiceOrderDTO> findAllOpenServices(){
    return repository.findByFinished(false).stream().map(this::toDto).toList();
  }

  private User getCurrentUser(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = ((UserDetailsAdapter) auth.getPrincipal()).getUser();

    return userRepository.findById(user.getId()).get();
  }

  public ServicePicture addPicture(Long orderId, PictureType type, MultipartFile file) throws Exception {

    ServiceOrder order = repository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("ServiceOrder não encontrada"));

    // Upload no MinIO
    String objectName = storageService.upload(
        file.getInputStream(),
        file.getSize(),
        file.getContentType()
    );

    try {
      // Persistir metadado no Postgres
      ServicePicture picture = new ServicePicture();
      picture.setObjectName(objectName);
      picture.setContentType(file.getContentType());
      picture.setServiceOrder(order);
      picture.setType(type);

      order.getPictures().add(picture);

      return pictureRepository.save(picture);

    } catch (Exception e) {
      // Rollback manual do MinIO
      storageService.delete(objectName);
      throw e;
    }
  }

  @Transactional(readOnly = true)
  public ServicePicture getPicture(Long pictureId) {
    return pictureRepository.findById(pictureId)
        .orElseThrow(() -> new RuntimeException("Imagem não encontrada"));
  }

  @Transactional(readOnly = true)
  public InputStream getPictureStream(Long pictureId) throws Exception {
    ServicePicture pic = pictureRepository.findById(pictureId)
        .orElseThrow(() -> new RuntimeException("Imagem não encontrada"));

    return storageService.download(pic.getObjectName());
  }

  @Override
  public ServiceOrderDTO toDto(ServiceOrder entity) {
    ServiceOrderDTO serviceOrderDTO = super.toDto(entity);
    serviceOrderDTO.setPictures(pictureRepository.findByServiceOrder_IdAndType(entity.getId(), PictureType.INITIAL).stream().map(ServicePicture::getId).toList());
    serviceOrderDTO.setConfirmProductionPictures(pictureRepository.findByServiceOrder_IdAndType(entity.getId(), PictureType.CONFIRM_PRODUCTION).stream().map(ServicePicture::getId).toList());
    serviceOrderDTO.setConfirmDeliverPictures(pictureRepository.findByServiceOrder_IdAndType(entity.getId(), PictureType.CONFIRM_DELIVER).stream().map(ServicePicture::getId).toList());
    return serviceOrderDTO;
  }
}
