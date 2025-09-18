package space.industock.industrial_stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.Product;
import space.industock.industrial_stock.dto.product.ProductGetDTO;
import space.industock.industrial_stock.dto.product.ProductPostDTO;
import space.industock.industrial_stock.exception.UnauthorizedException;
import space.industock.industrial_stock.mapper.ProductMapper;
import space.industock.industrial_stock.service.domain.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductManagerService {

  private final ProductService productService;
  private final ProductMapper productMapper;

  public ProductGetDTO saveProduct(ProductPostDTO productPostDTO){
    Product product = productMapper.toProduct(productPostDTO);
    return productMapper.toProductGetDTO(productService.save(product));
  }

  public ProductGetDTO replaceProduct(ProductPostDTO productPostDTO, Long id){
    Product product = productMapper.toProduct(productPostDTO);
    product.setId(id);
    return productMapper.toProductGetDTO(productService.save(product));
  }

  public List<ProductGetDTO> findAllProduct(){
    return productService.findAll().stream()
        .map(productMapper::toProductGetDTO)
        .collect(Collectors.toList());
  }

  public ProductGetDTO incrementProductAmount(Long id, Integer amount){
    Product product = productService.findById(id);
    product.setAmount(product.getAmount() + amount);
    return productMapper.toProductGetDTO(productService.save(product));
  }

  public ProductGetDTO decrementProductAmount(Long id, Integer amount){
    Product product = productService.findById(id);

    if(product.getAmount() < amount){
      throw new UnauthorizedException("Quantidade para retirada invalida ou acima do limite");
    }

    product.setAmount(product.getAmount() - amount);

    return productMapper.toProductGetDTO(productService.save(product));
  }

}
