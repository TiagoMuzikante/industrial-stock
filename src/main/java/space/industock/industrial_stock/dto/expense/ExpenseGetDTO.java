package space.industock.industrial_stock.dto.expense;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.industock.industrial_stock.enums.ExpenseType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseGetDTO {

  private String userName;
  private ExpenseType type;
  private Integer value;
  private String description;
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime createdAt;

}
