package space.industock.industrial_stock.service.session.commit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.industock.industrial_stock.domain.Client;
import space.industock.industrial_stock.domain.ServiceOrder;
import space.industock.industrial_stock.domain.ServicePicture;
import space.industock.industrial_stock.domain.session.UploadSession;
import space.industock.industrial_stock.domain.utils.FileUploadResult;
import space.industock.industrial_stock.dto.ClientDTO;
import space.industock.industrial_stock.dto.ServiceOrderDTO;
import space.industock.industrial_stock.dto.session.CommitResponse;
import space.industock.industrial_stock.dto.session.PictureMapping;
import space.industock.industrial_stock.enums.PictureType;
import space.industock.industrial_stock.enums.UploadSessionState;
import space.industock.industrial_stock.infra.JsonMapper;
import space.industock.industrial_stock.repository.ServicePictureRepository;
import space.industock.industrial_stock.repository.session.UploadSessionRepository;
import space.industock.industrial_stock.service.ClientService;
import space.industock.industrial_stock.service.ServiceOrderService;
import space.industock.industrial_stock.service.session.CommitSession;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceOrderCommit {

  private final UploadSessionRepository sessionRepository;
  private final ClientService clientService;
  private final ServiceOrderService serviceOrderService;
  private final ServicePictureRepository servicePictureRepository;
  private final CommitSession commitSession;

  public ServiceOrderCommit(UploadSessionRepository sessionRepository, ClientService clientService, ServiceOrderService serviceOrderService, ServicePictureRepository servicePictureRepository, CommitSession commitSession) {
    this.sessionRepository = sessionRepository;
    this.clientService = clientService;
    this.serviceOrderService = serviceOrderService;
    this.servicePictureRepository = servicePictureRepository;
    this.commitSession = commitSession;
  }

  @Transactional
  public CommitResponse commitService(UUID sessionId, PictureType type, ServiceOrder serviceOrder) {
    // Recupera a sessão
    UploadSession session = sessionRepository.findById(sessionId)
        .orElseThrow(() -> new IllegalArgumentException("UploadSession não encontrada"));

    // Commit genérico: finaliza os uploads e retorna metadados
    List<FileUploadResult> filesResult = commitSession.commitSession(sessionId);


    switch (type){
      case INITIAL -> initialCommit(session, filesResult);
      case CONFIRM_PRODUCTION -> confirmProductionCommit(session, filesResult, serviceOrder);
      case CONFIRM_DELIVER -> confirmDeliverCommit(session, filesResult, serviceOrder);
    }

    session.setState(UploadSessionState.COMMITTED);
    sessionRepository.save(session);

    CommitResponse res = new CommitResponse();
    res.pictures = filesResult.stream()
        .map(f -> {
          PictureMapping pm = new PictureMapping();
          pm.fileId = f.getFileId();
          pm.url = f.getObjectName();
          return pm;
        })
        .toList();

    return res;

  }

  @Transactional
  public CommitResponse commitService(UUID sessionId, PictureType type) {
    return this.commitService(sessionId, type, null);
  }

  private void confirmDeliverCommit(UploadSession session, List<FileUploadResult> filesResult, ServiceOrder service) {

    for (FileUploadResult fileResult : filesResult) {

    ServicePicture picture = new ServicePicture();
      picture.setType(PictureType.CONFIRM_DELIVER);
      picture.setServiceOrder(service);
      picture.setObjectName(fileResult.getObjectName());
      picture.setContentType(fileResult.getContentType());

      servicePictureRepository.save(picture);
    }

  }

  private void confirmProductionCommit(UploadSession session, List<FileUploadResult> filesResult, ServiceOrder service) {

    for (FileUploadResult fileResult : filesResult) {

    ServicePicture picture = new ServicePicture();
      picture.setType(PictureType.CONFIRM_PRODUCTION);
      picture.setServiceOrder(service);
      picture.setObjectName(fileResult.getObjectName());
      picture.setContentType(fileResult.getContentType());

      servicePictureRepository.save(picture);
    }

  }

  private void initialCommit(UploadSession session, List<FileUploadResult> filesResult){

    Client client = clientService.saveToEntity(
        JsonMapper.fromJson(session.getClientJson(), ClientDTO.class)
    );

    List<ServiceOrderDTO> serviceOrderDTOS =
        JsonMapper.listFromJson(session.getServicesJson(), ServiceOrderDTO.class);

    Map<String, ServiceOrder> savedServices = serviceOrderDTOS.stream()
        .peek(dto -> dto.setClient(client))
        .collect(Collectors.toMap(
            ServiceOrderDTO::getLocalId,
            serviceOrderService::saveToEntity
        ));

    for (FileUploadResult fileResult : filesResult) {
      ServiceOrder service = savedServices.get(fileResult.getLocalId().split("#")[0]);
      if (service == null) {
        throw new IllegalStateException("ServiceOrder não encontrada para localId: " + fileResult.getLocalId());
      }

      ServicePicture picture = new ServicePicture();
      picture.setType(PictureType.INITIAL);
      picture.setServiceOrder(service);
      picture.setObjectName(fileResult.getObjectName());
      picture.setContentType(fileResult.getContentType());

      servicePictureRepository.save(picture);
    }
  }
}
