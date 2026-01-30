package space.industock.industrial_stock.event;

import space.industock.industrial_stock.domain.Client;
import space.industock.industrial_stock.enums.Stage;

public record EnqueueClientServiceEvent(Client client, Stage stage) {
}
