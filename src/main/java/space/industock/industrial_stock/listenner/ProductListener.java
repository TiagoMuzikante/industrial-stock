package space.industock.industrial_stock.listenner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import space.industock.industrial_stock.event.ProductMovimentEvent;
import space.industock.industrial_stock.service.ProductHistoricService;

@Component
@RequiredArgsConstructor
public class ProductListener {

  private final ProductHistoricService productHistoricService;

  @TransactionalEventListener
  public void saveHistoricMoviment(ProductMovimentEvent productMovimentEvent){
    productHistoricService.saveProductHistoric(productMovimentEvent);
  }

}
