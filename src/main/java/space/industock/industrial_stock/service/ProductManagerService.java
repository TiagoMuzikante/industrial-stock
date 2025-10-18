package space.industock.industrial_stock.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import space.industock.industrial_stock.domain.Product;
import space.industock.industrial_stock.domain.ProductHistoric;
import space.industock.industrial_stock.domain.utils.UserDetailsAdapter;
import space.industock.industrial_stock.dto.ProductDTO;
import space.industock.industrial_stock.dto.productHistory.ProductHistoryDTO;
import space.industock.industrial_stock.event.ProductMovimentEvent;
import space.industock.industrial_stock.exception.UnauthorizedException;
import space.industock.industrial_stock.mapper.ProductHistoricMapper;
import space.industock.industrial_stock.mapper.ProductMapper;
import space.industock.industrial_stock.service.domain.ProductHistoricService;
import space.industock.industrial_stock.service.domain.ProductService;
import space.industock.industrial_stock.service.domain.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductManagerService {

  private final ProductService productService;
  private final UserService userService;
  private final ProductHistoricService productHistoricService;
  private final ProductMapper productMapper;
  private final ProductHistoricMapper productHistoricMapper;
  private final ApplicationEventPublisher publisher;

  public ProductDTO saveProduct(ProductDTO productPostDTO){
    Product product = productMapper.toProduct(productPostDTO);
    return productMapper.toDto(productService.save(product));
  }

  public ProductDTO replaceProduct(ProductDTO productPostDTO, Long id){
    Product product = productMapper.toProduct(productPostDTO);
    product.setId(id);
    return productMapper.toDto(productService.save(product));
  }

  public List<ProductDTO> findAllProduct(){
    return productService.findAll().stream()
        .map(productMapper::toDto)
        .collect(Collectors.toList());
  }

  public List<ProductHistoryDTO> findProductHistoric(String orderBy, LocalDate startDate, LocalDate endDate){
    if(orderBy.equals("product")){
      return productHistoricMapper.toHistoricByProductDTO(productService.findAll(), startDate, endDate);
    }else if (orderBy.equals("user")){
      return productHistoricMapper.toHistoricByUserDTO(userService.findAll(), startDate, endDate);
    }else {
      throw new UnauthorizedException("OrderBy is not accepted");
    }
  }


  @Transactional
  public ProductDTO incrementProductAmount(Long id, Integer amount){
    Product product = productService.findById(id);
    Integer oldAmount = product.getAmount();
    product.setAmount(product.getAmount() + amount);
    Product saved = productService.save(product);
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    publisher.publishEvent(new ProductMovimentEvent(saved, ((UserDetailsAdapter) auth.getPrincipal()).getUser(), product.getAmount(), oldAmount, amount,true));
    return productMapper.toDto(saved);
  }

  @Transactional
  public ProductDTO decrementProductAmount(Long id, Integer amount){
    Product product = productService.findById(id);
    if(product.getAmount() < amount){throw new UnauthorizedException("Quantidade para retirada invalida ou acima do limite");}
    Integer oldAmount = product.getAmount();
    product.setAmount(product.getAmount() - amount);
    Product saved = productService.save(product);
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    publisher.publishEvent(new ProductMovimentEvent(saved, ((UserDetailsAdapter) auth.getPrincipal()).getUser(), product.getAmount(), oldAmount, amount,false));
    return productMapper.toDto(saved);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void saveProductHistoric(ProductMovimentEvent productMovimentEvent){
    ProductHistoric productHistoric = new ProductHistoric(null,
        productMovimentEvent.amount(),
        productMovimentEvent.oldAmount(),
        productMovimentEvent.changedAmount(),
        LocalDateTime.now(),
        productMovimentEvent.isIncoming(),
        productMovimentEvent.product(),
        productMovimentEvent.user());

    productHistoricService.save(productHistoric);
  }
}
