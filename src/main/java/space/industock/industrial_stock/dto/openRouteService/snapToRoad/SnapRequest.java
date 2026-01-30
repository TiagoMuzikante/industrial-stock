package space.industock.industrial_stock.dto.openRouteService.snapToRoad;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SnapRequest {

  private List<List<Double>> locations;
  private Integer radius;

}
