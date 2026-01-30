package space.industock.industrial_stock.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.industock.industrial_stock.domain.Product;
import space.industock.industrial_stock.domain.utils.UserDetailsAdapter;
import space.industock.industrial_stock.dto.ProductDTO;
import space.industock.industrial_stock.dto.productHistory.ProductHistoryDTO;
import space.industock.industrial_stock.event.ProductMovimentEvent;
import space.industock.industrial_stock.exception.UnauthorizedException;
import space.industock.industrial_stock.repository.ProductRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService extends BaseService<Product, ProductDTO> {

  private final ProductRepository repository;
  private final ApplicationEventPublisher publisher;

  public ProductService(ProductRepository repository, ApplicationEventPublisher publisher) {
    super(repository);
    this.repository = repository;
    this.publisher = publisher;
  }

  @Transactional
  public ProductDTO incrementProductAmount(Long id, Integer amount){
    Product product = super.toEntity(super.find(id));
    Integer oldAmount = product.getAmount();
    product.setAmount(product.getAmount() + amount);
    Product saved = repository.save(product);
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    publisher.publishEvent(new ProductMovimentEvent(saved, ((UserDetailsAdapter) auth.getPrincipal()).getUser(), product.getAmount(), oldAmount, amount,true));
    return super.toDto(saved);
  }

  @Transactional
  public ProductDTO decrementProductAmount(Long id, Integer amount){
    Product product = super.toEntity(super.find(id));
    if(product.getAmount() < amount){throw new UnauthorizedException("Quantidade para retirada invalida ou acima do limite");}
    Integer oldAmount = product.getAmount();
    product.setAmount(product.getAmount() - amount);
    Product saved = repository.save(product);
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    publisher.publishEvent(new ProductMovimentEvent(saved, ((UserDetailsAdapter) auth.getPrincipal()).getUser(), product.getAmount(), oldAmount, amount,false));
    return super.toDto(saved);
  }

}
