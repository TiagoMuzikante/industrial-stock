package space.industock.industrial_stock.event;

import space.industock.industrial_stock.domain.Product;
import space.industock.industrial_stock.domain.User;

public record ProductMovimentEvent(Product product, User user, Integer amount, Integer oldAmount, Integer changedAmount, Boolean isIncoming) {
}
