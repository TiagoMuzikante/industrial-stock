package space.industock.industrial_stock.event;

import space.industock.industrial_stock.domain.ServiceOrder;

public record NextQueueServiceEvent(ServiceOrder serviceOrder) {
}
