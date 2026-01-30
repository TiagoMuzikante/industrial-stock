package space.industock.industrial_stock.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.domain.utils.AddressDetails;
import space.industock.industrial_stock.dto.routes.LocationSnap;
import space.industock.industrial_stock.dto.routes.OptimizeRoute;
import space.industock.industrial_stock.dto.routes.OptimizeRouteResponse;
import space.industock.industrial_stock.service.external.GeoLocationService;

import java.util.List;

@RestController
@RequestMapping("/geo-location")
@RequiredArgsConstructor
@Log4j2
public class GeoLocationController {

  private final GeoLocationService service;

  @GetMapping("/geo-code/reverse/{lng}/{lat}")
  @PreAuthorize("hasAuthority('GEO_USE')")
  public ResponseEntity<AddressDetails> getAddressByLatLng(@PathVariable(required = true) double lat, @PathVariable(required = true) double lng){
    return ResponseEntity.ok(service.getAddressByLatLng(List.of(lng,lat)));
  }
  @PostMapping("/route")
  @PreAuthorize("hasAuthority('GEO_USE')")
  public ResponseEntity<OptimizeRouteResponse> optimizeRoute(@RequestBody OptimizeRoute request){
    return ResponseEntity.ok(service.optimizationRoute(request));
  }

  @GetMapping("/snap-to-road/{lng}/{lat}")
  @PreAuthorize("hasAuthority('GEO_USE')")
  public ResponseEntity<LocationSnap> snapToRoad(@PathVariable(required = true) Double lng, @PathVariable(required = true) Double lat){
    log.info(lng + " - " + lat);
    return ResponseEntity.ok(service.snapToRoad(List.of(lng, lat)));
  }

}
