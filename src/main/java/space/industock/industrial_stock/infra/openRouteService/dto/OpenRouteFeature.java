package space.industock.industrial_stock.infra.openRouteService.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenRouteFeature {
  private OpenRouteGeometry geometry;
  private OpenRouteProperties properties;
}
