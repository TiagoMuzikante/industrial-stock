package space.industock.industrial_stock.mapper;

import org.mapstruct.Mapper;
import space.industock.industrial_stock.domain.Product;
import space.industock.industrial_stock.dto.ProductDTO;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

  public abstract Product toProduct(ProductDTO productDTO);

  public abstract ProductDTO toDto(Product product);

}
