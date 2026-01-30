package space.industock.industrial_stock.dto.openRouteService.optimization;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Step {

  private String type; // "start", "job", "end"
  private List<Double> location; // [lon, lat]
  private Integer id;
  private Integer job;
  private double arrival;
  private double duration;

}
