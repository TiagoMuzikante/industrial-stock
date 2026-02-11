package space.industock.industrial_stock.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import space.industock.industrial_stock.domain.Client;
import space.industock.industrial_stock.domain.ServiceOrder;
import space.industock.industrial_stock.domain.User;
import space.industock.industrial_stock.domain.queue.PriorityBlock;
import space.industock.industrial_stock.domain.queue.PriorityBlockItem;
import space.industock.industrial_stock.domain.queue.QueueItem;
import space.industock.industrial_stock.domain.utils.UserDetailsAdapter;
import space.industock.industrial_stock.dto.ClientDTO;
import space.industock.industrial_stock.dto.ProductDTO;
import space.industock.industrial_stock.dto.QueueItemDTO;
import space.industock.industrial_stock.dto.ServiceOrderDTO;
import space.industock.industrial_stock.enums.Stage;
import space.industock.industrial_stock.event.NextQueueServiceEvent;
import space.industock.industrial_stock.event.ServiceOrderHistoryEvent;
import space.industock.industrial_stock.exception.InternalAuthorizedException;
import space.industock.industrial_stock.repository.ClientRepository;
import space.industock.industrial_stock.repository.ServiceOrderRepository;
import space.industock.industrial_stock.repository.UserRepository;
import space.industock.industrial_stock.repository.queue.PriorityBlockItemRepository;
import space.industock.industrial_stock.repository.queue.PriorityBlockRepository;
import space.industock.industrial_stock.repository.queue.QueueItemRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class QueueService {

  private final QueueItemRepository queueRepo;
  private final PriorityBlockRepository blockRepo;
  private final PriorityBlockItemRepository blockItemRepo;
  private final ServiceOrderRepository orderRepo;
  private final ApplicationEventPublisher publisher;
  private final ClientRepository clientRepository;
  private final UserRepository userRepository;

  /* ============================================================
   * INICIA PRODUÇÂO
   * ============================================================ */
  @Transactional
  public void startProduction(Long queueId){
    User user = getCurrentUser();

    QueueItem queueItem = queueRepo.findById(queueId).orElseThrow(() -> new InternalAuthorizedException("QueueItem não encontrado"));

    if(user.getCurrentServices().stream().anyMatch((queueItem.getClient().getServices())::contains)){
      throw new InternalAuthorizedException("Usuario já esta neste serviço");
    }
    if(queueItem.getClient().getServices().stream().anyMatch(aux -> aux.getCurrentUser() != null)){
      throw new InternalAuthorizedException("Serviço já foi iniciado");
    }
    if(!user.getCurrentServices().isEmpty()){
      throw new InternalAuthorizedException("Usuario já esta em um serviço");
    }

    for(ServiceOrder aux : queueItem.getClient().getServices().stream().filter(so -> so.getStage() == Stage.PENDENTE_PRODUCAO).toList()){
      aux.setCurrentUser(user);
      publisher.publishEvent(new NextQueueServiceEvent(aux));
    }

  }

  /* ============================================================
   * ENFILEIRAR CLIENTE NO STAGE
   * ============================================================ */
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void enqueue(Client client, Stage stage) {

    // cliente já está na fila?
    Optional<QueueItem> existing = queueRepo.findByClientIdAndStage(client.getId(), stage);
    if (existing.isPresent()) {
      publishHistoryEvent(existing.get(), "Serviço adicionado ao cliente já na fila");
      return;
    }

    long count = queueRepo.countByStage(stage);

    QueueItem item = new QueueItem();
    item.setClient(client);
    item.setStage(stage);
    item.setOriginalPosition(count + 1);

    queueRepo.save(item);

    publishHistoryEvent(item, "Cliente entrou na fila do stage");
  }


  /* ============================================================
   * BUSCAR A FILA FINAL (CLIENTES)
   * ============================================================ */
  public List<QueueItemDTO> getQueue(Stage stage) {
      List<QueueItem> ordered = queueRepo.findAllOrdered(stage);
      return ordered.stream()
          .map(service -> toQueueDTO(service, false))
          .toList();
  }

  public QueueItemDTO findItem(Long id){
    QueueItem queueItem = queueRepo.findById(id).orElseThrow(() -> new InternalAuthorizedException("Não encontrado."));
    return toQueueDTO(queueItem, false);
  }



  /* ============================================================
   * BUSCA SERVIÇO ATUAL
   * ============================================================ */
  public QueueItemDTO CurrentProduction() {
    User user = getCurrentUser();

    if(!user.getCurrentServices().isEmpty()){
      User currentUser = userRepository.findById(user.getId()).get();
      QueueItem item = queueRepo.findByClientIdAndStage(user.getCurrentServices().getFirst().getClient().getId(), Stage.EM_PRODUCAO).orElseThrow(() -> new InternalAuthorizedException("Queue nao encontrada"));
      return toQueueDTO(item, false);
    }

    return null;
  }


  /* ================ ============================================
   * MOVER APENAS O SERVIÇO PARA OUTRO STAGE
   * ============================================================ */
  public void moveToNextStage(Long id){
    ServiceOrder serviceOrder = orderRepo.findById(id).orElseThrow(() -> new InternalAuthorizedException("Serviço não encontrado."));
    this.moveToNextStage(serviceOrder);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void moveToNextStage(ServiceOrder order) {
    Stage oldStage = order.getStage() == null ? Stage.PENDENTE_PRODUCAO : order.getStage();
    Stage newStage = oldStage.next();

    handleQueueMove(order, oldStage, newStage);
    order.setStage(newStage);

    //definição do atuador
    if(oldStage == Stage.EM_PRODUCAO){
      order.setProductedByUser(this.getCurrentUser());
    }

    orderRepo.save(order);
    enqueue(order.getClient(), order.getStage());
  }

  private void handleQueueMove(ServiceOrder so,Stage oldStage, Stage newStage) {

    Long clientId = so.getClient().getId();

    // existem mais serviços desse cliente nesse stage?
    List<ServiceOrder> othersServices = orderRepo.findServiceOrdersByClientIdAndStage(clientId, oldStage);
    othersServices.removeIf(i -> i.getId().equals(so.getId()));

    if (othersServices.isEmpty()) {
      // remover cliente do stage antigo
      removeFromClientOldQueue(clientId, oldStage);
    }
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public boolean removeFromClientOldQueue(Long clientId, Stage stage){
    Optional<QueueItem> qi = queueRepo.findByClientIdAndStage(clientId, stage);
    if (qi.isPresent()) {
      removeFromBlock(qi.get().getId());
      queueRepo.deleteById(qi.get().getId());
      return true;
    }
    return false;
  }

  /* ============================================================
   * ADD TO BLOCK
   * ============================================================ */
  @Transactional
  public void addToBlock(Long blockId, Long queueItemId, int position) {

    PriorityBlock block = blockRepo.findById(blockId)
        .orElseThrow(() -> new RuntimeException("Bloco não encontrado"));

    QueueItem item = queueRepo.findById(queueItemId)
        .orElseThrow(() -> new RuntimeException("Item não encontrado"));

    if (item.getBlock() != null) {
      removeFromBlock(item.getId());
    }

    item.setBlock(block);

    PriorityBlockItem pbi = new PriorityBlockItem();
    pbi.setBlock(block);
    pbi.setItem(item);
    pbi.setPosition(position);

    blockItemRepo.save(pbi);
    queueRepo.save(item);

    publishHistoryEvent(item,
        "Cliente adicionado ao bloco " + block.getName());
  }


  /* ============================================================
   * REMOVER DO BLOCO
   * ============================================================ */
  @Transactional
  public void removeFromBlock(Long queueItemId) {

    QueueItem item = queueRepo.findById(queueItemId)
        .orElseThrow(() -> new RuntimeException("Item não encontrado"));

    if (item.getBlock() == null) return;

    PriorityBlock block = item.getBlock();

    block.getItems().removeIf(pbi -> pbi.getItem().getId().equals(queueItemId));
    item.setBlock(null);

    queueRepo.save(item);

    publishHistoryEvent(item,
        "Cliente removido do bloco " + block.getName());
  }

  /* ============================================================
   * PUBLICAR EVENTO DE HISTÓRICO
   * ============================================================ */

  private void publishHistoryEvent(QueueItem item, String notes) {
    publishHistoryEvent(item, notes, null, item.getStage(), item.getStage());
  }

  public long countByStage(Stage stage){
    return queueRepo.countByStage(stage);
  }



  private QueueItemDTO toQueueDTO(QueueItem item, boolean onlyOpenedServices) {
    QueueItemDTO dto = BaseService.toDto(item, QueueItemDTO.class);
    Client client = clientRepository.findById(item.getClient().getId()).orElseThrow(() -> new InternalAuthorizedException("Cliente não encontrado."));

    dto.setClient(BaseService.toDto(client, ClientDTO.class));

    // Busca todos os serviços do cliente para esse stage
    List<ServiceOrder> orders = orderRepo.findByClientAndStage(client, item.getStage());

    if (onlyOpenedServices){

      dto.setServiceOrder(
          orders.stream().filter(service -> service.getStage() == Stage.EM_PRODUCAO)
              .map(o -> BaseService.toDto(o, ServiceOrderDTO.class))
              .peek(sod -> sod.setMateriais(sod.getMaterials().stream().map(aux -> BaseService.toDto(aux, ProductDTO.class)).toList()))
              .toList()
      );
    } else {
      dto.setServiceOrder(
          orders.stream()
              .map(o -> BaseService.toDto(o, ServiceOrderDTO.class))
              .peek(sod -> sod.setMateriais(sod.getMaterials().stream().map(aux -> BaseService.toDto(aux, ProductDTO.class)).toList()))
              .toList()
      );
    }
    dto.setStarted(client.getServices().stream().anyMatch(aux -> aux.getCurrentUser() != null));

    return dto;
  }


  private void publishHistoryEvent(
      QueueItem item,
      String notes,
      User user,
      Stage oldStage,
      Stage newStage
  ) {
    publisher.publishEvent(new ServiceOrderHistoryEvent(
        user,
        null, // histórico não é mais por serviço
        oldStage,
        newStage,
        item.getOriginalPosition(),
        item.getOriginalPosition(),
        item.getOriginalPosition(),
        item.getBlock(),
        item,
        notes
    ));
  }

  private User getCurrentUser(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = ((UserDetailsAdapter) auth.getPrincipal()).getUser();

    return userRepository.findById(user.getId()).get();
  }

}