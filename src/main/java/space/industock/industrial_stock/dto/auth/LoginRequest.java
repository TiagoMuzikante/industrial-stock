package space.industock.industrial_stock.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

  private String name;
  private String password;
  private String frontSecret;

}
