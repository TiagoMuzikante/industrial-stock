package space.industock.industrial_stock.dto.routes;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OptimizeRoute {

  private List<Double> start;
  private List<Double> end;

  private List<Point> points;

  public boolean startPreset(){
    return this.start != null;
  }
  public boolean endPresent(){
    return this.end != null;
  }

}
