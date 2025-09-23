package space.industock.industrial_stock.service.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.ProductHistoric;
import space.industock.industrial_stock.repository.ProductHistoricRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductHistoricService {

  private final ProductHistoricRepository productHistoricRepository;

  public ProductHistoric save(ProductHistoric productHistoric){
    return productHistoricRepository.save(productHistoric);
  }

}
