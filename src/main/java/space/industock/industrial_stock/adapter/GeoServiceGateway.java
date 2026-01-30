package space.industock.industrial_stock.adapter;

import space.industock.industrial_stock.domain.utils.AddressDetails;
import space.industock.industrial_stock.dto.routes.LocationSnap;
import space.industock.industrial_stock.dto.routes.OptimizeRoute;
import space.industock.industrial_stock.dto.routes.OptimizeRouteResponse;

import java.util.List;

public interface GeoServiceGateway {

  AddressDetails getAddressByLatLng(List<Double> coords);

  OptimizeRouteResponse optimizationRoute(OptimizeRoute optimizeRoute);

  LocationSnap snapToRoad(List<Double> coords);

}
