package space.industock.industrial_stock.dto.openRouteService.snapToRoad;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import space.industock.industrial_stock.dto.routes.LocationSnap;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SnapResponse {

  private List<LocationSnap> locations;

  public LocationSnap getSnappedLocationSnap(){
    return locations.getFirst();
  }

}
