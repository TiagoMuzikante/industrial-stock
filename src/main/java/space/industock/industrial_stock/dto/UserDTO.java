package space.industock.industrial_stock.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import space.industock.industrial_stock.domain.Role;
import space.industock.industrial_stock.enums.RoleEnum;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserDTO {

  private Long id;

  @NotBlank(message = "O nome não pode estar em branco.")
  private String name;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Role role;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String accessLevel;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private boolean isEnable = true;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private List<RoleEnum> authorities;

}