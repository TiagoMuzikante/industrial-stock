package space.industock.industrial_stock.dto.openRouteService.optimization;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OptimizationResponse {

  private List<Route> routes;

}


