package space.industock.industrial_stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.industock.industrial_stock.enums.RoleEnum;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

  private UUID id;
  private String name;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private List<RoleEnum> authorities;

}
