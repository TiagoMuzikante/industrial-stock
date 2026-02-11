package space.industock.industrial_stock.listenner;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import space.industock.industrial_stock.event.CommitServicePicturesEvent;
import space.industock.industrial_stock.event.RemoveClientFromEnqueueEvent;
import space.industock.industrial_stock.service.session.commit.ServiceOrderCommit;

@Component
@RequiredArgsConstructor
public class ServiceOrderListener {

  private final ServiceOrderCommit serviceOrderCommit;

  @EventListener
  public void servicePictureCommit(CommitServicePicturesEvent event){
    serviceOrderCommit.commitService(event.sessionId(), event.pictureType(), event.serviceOrder());
  }

//  @EventListener
//  public void handleHistory(ServiceOrderHistoryEvent event) {
//
//    QueueHistory hist = new QueueHistory();
//
//    hist.setQueueItem(event.queueItem());
//    hist.setExecutedAt(LocalDateTime.now());
//    hist.setPositionAtExecution(event.oldPosition());
//    hist.setBlockAtExecution(event.block());
//    hist.setNotes(event.notes());
//
//    historyRepo.save(hist);
//  }

}
