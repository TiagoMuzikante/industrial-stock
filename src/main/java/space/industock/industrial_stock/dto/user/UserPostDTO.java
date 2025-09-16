package space.industock.industrial_stock.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPostDTO {

  private String name;
  private String email;
  private String document_cpf;
  private String password;

}
