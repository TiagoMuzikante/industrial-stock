package space.industock.industrial_stock.event;

import space.industock.industrial_stock.domain.ServiceOrder;
import space.industock.industrial_stock.domain.User;
import space.industock.industrial_stock.domain.queue.PriorityBlock;
import space.industock.industrial_stock.domain.queue.QueueItem;
import space.industock.industrial_stock.enums.Stage;

public record ServiceOrderHistoryEvent(User user,
                                       ServiceOrder serviceOrder,
                                       Stage oldStage,
                                       Stage newStage,
                                       Long oldPosition,
                                       Long newPosition,
                                       Long executedAtPosition,
                                       PriorityBlock oldBlock,
                                       QueueItem queueItem,
                                       String notes) { }
