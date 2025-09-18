package space.industock.industrial_stock.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.industock.industrial_stock.dto.user.UserGetDTO;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenResponse {

  private String token;
  private UserGetDTO user;

}
