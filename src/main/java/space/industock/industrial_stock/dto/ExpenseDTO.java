package space.industock.industrial_stock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class ExpenseDTO {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String userName;

  @NotNull(message = "O tipo precisa precisa ser preenchido.")
  private ExpenseType type;

  @Min(value = 1, message = "O valor não pode ser menor do que 1.")
  private Integer value;

  private String description;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
  private LocalDateTime createdAt;

}
