package space.industock.industrial_stock.dto.auth;

import lombok.*;
import space.industock.industrial_stock.dto.UserDTO;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenResponse {

  private String token;
  private UserDTO user;

}
