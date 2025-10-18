package space.industock.industrial_stock.service.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.Product;
import space.industock.industrial_stock.exception.UnauthorizedException;
import space.industock.industrial_stock.mapper.ProductMapper;
import space.industock.industrial_stock.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public Product save(Product product){
    return productRepository.save(product);
  }

  public Product findById(Long id){
    return productRepository.findById(id).orElseThrow(() -> new UnauthorizedException("Produto não encontrado"));
  }

  public List<Product> findAll(){
    return productRepository.findAll();
  }

}
