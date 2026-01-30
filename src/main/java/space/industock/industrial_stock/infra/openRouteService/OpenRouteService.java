package space.industock.industrial_stock.infra.openRouteService;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import space.industock.industrial_stock.adapter.GeoServiceGateway;
import space.industock.industrial_stock.domain.utils.AddressDetails;
import space.industock.industrial_stock.dto.openRouteService.optimization.OptimizationRequest;
import space.industock.industrial_stock.dto.openRouteService.optimization.OptimizationResponse;
import space.industock.industrial_stock.dto.openRouteService.snapToRoad.SnapRequest;
import space.industock.industrial_stock.dto.openRouteService.snapToRoad.SnapResponse;
import space.industock.industrial_stock.dto.routes.LocationSnap;
import space.industock.industrial_stock.dto.routes.OptimizeRoute;
import space.industock.industrial_stock.dto.routes.OptimizeRouteResponse;
import space.industock.industrial_stock.exception.GeoCodeNotValidException;
import space.industock.industrial_stock.infra.openRouteService.dto.OpenRouteResponse;
import space.industock.industrial_stock.mapper.GeoCode;

import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class OpenRouteService implements GeoServiceGateway {

  @Value(value = "${jwt.secretTokenOpenRouteService}")
  private String openRouteServiceToken;

  private String baseUrl = "https://api.openrouteservice.org";
  private Integer tolerableRadius = 35;
  private RestTemplate restTemplate = new RestTemplate();
  private HttpHeaders headers = new HttpHeaders();

  public OptimizeRouteResponse optimizationRoute(OptimizeRoute optimizeRoute){

    try {
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.set("Authorization", openRouteServiceToken);

      OptimizationRequest body = new OptimizationRequest(optimizeRoute);
      HttpEntity<OptimizationRequest> request = new HttpEntity<>(body, headers);

      OptimizationResponse response = restTemplate.postForObject(baseUrl+"/optimization", request, OptimizationResponse.class);

      log.info(response);

      assert response != null;
      return new OptimizeRouteResponse(response);
    } catch (Exception e) {
      throw new RuntimeException("Erro ao consultar OpenRouteService", e);
    }
  }


  public LocationSnap snapToRoad(List<Double> coords){
    try {
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.set("Authorization", openRouteServiceToken);

      SnapRequest body = new SnapRequest(List.of(coords), tolerableRadius);
      HttpEntity<SnapRequest> request = new HttpEntity<>(body, headers);

      SnapResponse response = restTemplate.postForObject(baseUrl+"/v2/snap/driving-car", request, SnapResponse.class);

      log.info(response);

      if(response == null || response.getLocations().isEmpty() || response.getLocations().getFirst() == null){
        throw new GeoCodeNotValidException("Localização invalida ou muito distante de uma rua.", coords);
      }

      return response.getSnappedLocationSnap();
    } catch (Exception e) {
      throw new RuntimeException("Erro ao consultar OpenRouteService", e);
    }
  }


  @Override
  public AddressDetails getAddressByLatLng(List<Double> coords) {

    try {
      String url = UriComponentsBuilder
          .fromHttpUrl("https://api.openrouteservice.org/geocode/reverse")
          .queryParam("api_key", openRouteServiceToken)
          .queryParam("point.lat", coords.get(1))
          .queryParam("point.lon", coords.get(0))
          .build()
          .toUriString();

      log.info(url);
      OpenRouteResponse response = restTemplate.getForObject(url, OpenRouteResponse.class);

      return GeoCode.toAddressDetails(response);
    } catch (Exception e) {
      throw new RuntimeException("Erro ao consultar OpenRouteService", e);
    }
  }

  private static String buildUrl(String baseUrl, Map<String, String> params) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
    params.forEach(builder::queryParam);
    return builder.toUriString();
  }


}
