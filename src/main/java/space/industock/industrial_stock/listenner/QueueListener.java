package space.industock.industrial_stock.listenner;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import space.industock.industrial_stock.event.EnqueueClientServiceEvent;
import space.industock.industrial_stock.event.NextQueueServiceEvent;
import space.industock.industrial_stock.service.QueueService;

@Component
@RequiredArgsConstructor
public class QueueListener {

  private final QueueService service;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void enqueueService(EnqueueClientServiceEvent event){
    service.enqueue(event.client(), event.stage());
  }

  @EventListener
  public void nextQueueStage(NextQueueServiceEvent event){
    service.moveToNextStage(event.serviceOrder());
  }

}
