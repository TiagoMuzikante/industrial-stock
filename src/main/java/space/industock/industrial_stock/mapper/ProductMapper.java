package space.industock.industrial_stock.mapper;

import org.mapstruct.Mapper;
import space.industock.industrial_stock.domain.Product;
import space.industock.industrial_stock.dto.product.ProductGetDTO;
import space.industock.industrial_stock.dto.product.ProductPostDTO;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

  public abstract Product toProduct(ProductPostDTO productPostDTO);

  public abstract ProductGetDTO toProductGetDTO(Product product);

}
