package space.industock.industrial_stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import space.industock.industrial_stock.domain.Client;
import space.industock.industrial_stock.domain.Product;
import space.industock.industrial_stock.domain.ServicePicture;
import space.industock.industrial_stock.enums.Stage;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ServiceOrderDTO {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  @NotBlank(message = "o modelo nao pode permanecer em branco")
  private String model;

  @NotNull(message = "O valor nao pode permanecer em branco")
  private Integer valueInCents;

  private String observation;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private boolean finished;

  private Stage stage;

  @NotNull(message = "O material precisa ser selecionado.")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<Product> materials;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private List<ProductDTO> materiais;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private LocalDateTime createdAt;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private List<Long> pictures;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private List<Long>  confirmProductionPictures;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private List<Long>  confirmDeliverPictures;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Client client;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UserDTO productedByUser;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UserDTO currentUser;

  private String localId;

  private Integer multiplier;

}
