package space.industock.industrial_stock.dto.routes;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Embeddable
public class LngLat {

  public Double lng;
  public Double lat;

}
