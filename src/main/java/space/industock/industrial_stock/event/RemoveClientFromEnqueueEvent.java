package space.industock.industrial_stock.event;

import space.industock.industrial_stock.enums.Stage;

public record RemoveClientFromEnqueueEvent(Long clientId, Stage stage) {
}
