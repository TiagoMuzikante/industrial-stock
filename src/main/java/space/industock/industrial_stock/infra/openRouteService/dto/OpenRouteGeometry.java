package space.industock.industrial_stock.infra.openRouteService.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenRouteGeometry {
  private List<Double> coordinates;
}
