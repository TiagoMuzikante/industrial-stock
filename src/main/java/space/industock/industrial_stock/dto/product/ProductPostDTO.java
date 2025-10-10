package space.industock.industrial_stock.dto.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPostDTO {

  @NotEmpty(message = "O nome não pode estar em branco.")
  @Size(min = 3, message = "O nome precisa conter no minimo 3 caracteres.")
  private String name;
  private Integer amount;

}
