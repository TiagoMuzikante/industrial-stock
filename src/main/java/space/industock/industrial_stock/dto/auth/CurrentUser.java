package space.industock.industrial_stock.dto.auth;

import lombok.*;
import space.industock.industrial_stock.dto.UserDTO;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentUser {

  private boolean expired;
  private List<String> authorities;
  private UserDTO user;

}
