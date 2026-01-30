package space.industock.industrial_stock.dto.openRouteService.optimization;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import space.industock.industrial_stock.dto.routes.OptimizeRoute;
import space.industock.industrial_stock.dto.routes.Point;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class OptimizationRequest {

  private List<Job> jobs;
  private List<Vehicle> vehicles;

  public OptimizationRequest(OptimizeRoute or){
    this.jobs = new ArrayList<>();

    log.info(or.toString());
    log.info(or.getPoints().toString());

    for (Point point : or.getPoints()) {
      jobs.add(new Job(point.getId(), point.getLocation()));
    }

    log.info(this.jobs.toString());

//    this.vehicles = List.of(new Vehicle(1,"driving-car", List.of(or.getStart().get(0), or.getStart().get(1)), List.of(or.getEnd().get(0), or.getEnd().get(1))));
    this.vehicles = List.of(Vehicle.builder().id(1).profile("driving-car").start(or.getStart() != null ? or.getStart() : null).end(or.getEnd() != null ? or.getEnd() : null).build());
  }

}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class Job {

  private int id;
  private List<Double> location;

}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Vehicle {

  private int id;
  private String profile;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<Double> start;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<Double> end;


}
