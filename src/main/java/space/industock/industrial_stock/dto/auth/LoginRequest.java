package space.industock.industrial_stock.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

  @NotEmpty(message = "O nome não pode estar em branco.")
  private String name;
  @NotEmpty(message = "A senha não pode estar em branco.")
  private String password;
  @NotEmpty(message = "O Secret deve estar presente")
  private String frontSecret;

}
