package space.industock.industrial_stock.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeoCodeNotValidException extends InternalAuthorizedException {

  private List<Double> geoCode;

  public GeoCodeNotValidException(String message, List<Double> coords) {
    super(message);
    this.geoCode = coords;
  }
}
