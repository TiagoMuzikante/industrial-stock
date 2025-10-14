package space.industock.industrial_stock.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.industock.industrial_stock.dto.user.UserGetDTO;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentUser {

  private boolean expired;
  private List<String> authorities;
  private UserGetDTO user;

}
