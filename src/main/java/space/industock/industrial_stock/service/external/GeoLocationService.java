package space.industock.industrial_stock.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.adapter.GeoServiceGateway;
import space.industock.industrial_stock.domain.utils.AddressDetails;
import space.industock.industrial_stock.dto.routes.LocationSnap;
import space.industock.industrial_stock.dto.routes.OptimizeRoute;
import space.industock.industrial_stock.dto.routes.OptimizeRouteResponse;
import space.industock.industrial_stock.infra.externalUseCases.GeoLocationUseCase;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoLocationService implements GeoLocationUseCase {

  private final GeoServiceGateway geoServiceGatway;

  @Override
  public LocationSnap snapToRoad(List<Double> coords) {
    return geoServiceGatway.snapToRoad(coords);
  }

  @Override
  public AddressDetails getAddressByLatLng(List<Double> coords) {
    return geoServiceGatway.getAddressByLatLng(coords);
  }

  @Override
  public OptimizeRouteResponse optimizationRoute(OptimizeRoute optimizeRoute) {
    return geoServiceGatway.optimizationRoute(optimizeRoute);
  }
}
