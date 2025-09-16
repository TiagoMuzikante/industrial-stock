package space.industock.industrial_stock.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGetDTO {

  private UUID id;
  private String name;
  private String email;
  private String document_cpf;

}
