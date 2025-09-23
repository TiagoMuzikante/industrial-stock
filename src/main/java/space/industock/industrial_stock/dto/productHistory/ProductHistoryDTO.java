package space.industock.industrial_stock.dto.productHistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductHistoryDTO {

  private String id;
  private String name;
  private List<HistoricInnerDTO> historics;

}
