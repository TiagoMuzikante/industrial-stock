package space.industock.industrial_stock.dto.expense;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.industock.industrial_stock.enums.ExpenseType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpensePostDTO {

  @NotNull(message = "O tipo precisa precisa ser preenchido.")
  private ExpenseType type;

  @NotNull(message = "O valor precisa precisa ser preenchido.")
  @Min(value = 1, message = "O valor não pode ser menor do que 1.")
  private Integer value;

}
