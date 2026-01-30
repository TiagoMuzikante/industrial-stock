package space.industock.industrial_stock.dto.routes;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import space.industock.industrial_stock.dto.openRouteService.optimization.OptimizationResponse;
import space.industock.industrial_stock.dto.openRouteService.optimization.Step;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class OptimizeRouteResponse {
  private List<Point> points;

  public OptimizeRouteResponse(OptimizationResponse response){
    this.points = new ArrayList<>();

    for (Step s : response.getRoutes().getFirst().getSteps()) {

      if(s.getType().equals("job")){
        points.add(Point.builder().type(s.getType()).job(s.getJob()).location(s.getLocation()).build());
      }else {
        points.add(Point.builder().type(s.getType()).location(s.getLocation()).build());
      }
//
//      switch (s.getType()) {
//        case "start" -> this.start = s.getLocation();
//        case "end" -> this.end = s.getLocation();
//        case "job" -> points.add(new Point(s.getJob(), s.getLocation()));
//      }
    }
    log.info(this.toString());
  }

}
