package space.industock.industrial_stock.listenner;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import space.industock.industrial_stock.event.ProductMovimentEvent;
import space.industock.industrial_stock.service.ProductManagerService;

@Component
@RequiredArgsConstructor
public class ProductListener {

  private final ProductManagerService productManagerService;

  @TransactionalEventListener
  public void saveHistoricMoviment(ProductMovimentEvent productMovimentEvent){
    productManagerService.saveProductHistoric(productMovimentEvent);
  }

}
