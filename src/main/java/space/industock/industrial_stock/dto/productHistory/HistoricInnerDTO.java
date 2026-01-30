package space.industock.industrial_stock.dto.productHistory;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
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
