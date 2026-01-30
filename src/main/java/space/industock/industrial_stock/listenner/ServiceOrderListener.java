package space.industock.industrial_stock.listenner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceOrderListener {

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
