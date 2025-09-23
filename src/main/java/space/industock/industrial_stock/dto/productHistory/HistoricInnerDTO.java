package space.industock.industrial_stock.dto.productHistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricInnerDTO {

  private String name;
  private Boolean isIncoming;
  private Integer amount;
  private Integer oldAmount;
  private Integer changedAmount;
  private LocalDate date;

}
