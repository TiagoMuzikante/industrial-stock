package space.industock.industrial_stock.dto.routes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationSnap {
  private List<Double> location;
  private String name;
  @JsonProperty("snapped_distance")
  private double snappedDistance;
}
