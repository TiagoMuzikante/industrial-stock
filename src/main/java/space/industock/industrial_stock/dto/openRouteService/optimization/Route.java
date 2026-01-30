package space.industock.industrial_stock.dto.openRouteService.optimization;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Route {

  private int vehicle;
  private double cost;
  private double duration;
  private List<Step> steps;

}
