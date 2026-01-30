package space.industock.industrial_stock.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductDTO {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  @NotEmpty(message = "O nome não pode estar em branco.")
  @Size(min = 3, message = "O nome precisa conter no minimo 3 caracteres.")
  private String name;
  private Integer amount;

}
