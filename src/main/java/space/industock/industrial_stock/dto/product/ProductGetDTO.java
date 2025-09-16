package space.industock.industrial_stock.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductGetDTO {

  private Long id;
  private String name;
  private Integer amount;

}
