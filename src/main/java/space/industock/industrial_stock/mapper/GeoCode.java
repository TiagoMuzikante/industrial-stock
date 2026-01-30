package space.industock.industrial_stock.mapper;

import space.industock.industrial_stock.domain.utils.AddressDetails;
import space.industock.industrial_stock.infra.openRouteService.dto.OpenRouteFeature;
import space.industock.industrial_stock.infra.openRouteService.dto.OpenRouteProperties;
import space.industock.industrial_stock.infra.openRouteService.dto.OpenRouteResponse;

import java.util.List;

public class GeoCode {

  public static AddressDetails toAddressDetails(OpenRouteResponse response) {
    if (response == null || response.getFeatures() == null || response.getFeatures().isEmpty()) return null;

    AddressDetails addressDetails = new AddressDetails();
    OpenRouteFeature feature = response.getFeatures().get(0); // usa o primeiro (mais relevante)
    OpenRouteProperties props = feature.getProperties();

    if (props != null) {
      addressDetails.setRoute(props.getStreet());
      addressDetails.setNeighborhood(coalesce(
          props.getNeighbourhood(),
          props.getBorough(),
          props.getLocaladmin()
      ));
      addressDetails.setCity(coalesce(
          props.getLocality(),
          props.getRegion(),
          props.getCounty()
      ));
      addressDetails.setState(coalesce(
          props.getMacroregion(),
          props.getRegion()
      ));
      addressDetails.setCountry(props.getCountry());
      addressDetails.setPostalCode(props.getPostalcode());
    }

    if (feature.getGeometry() != null && feature.getGeometry().getCoordinates() != null) {
      List<Double> coords = feature.getGeometry().getCoordinates();
      if (coords.size() >= 2) {
        addressDetails.setLng(coords.get(0));
        addressDetails.setLat(coords.get(1));
      }
    }
    return addressDetails;
  }


  private static String coalesce(String... values) {
    for (String v : values) {
      if (v != null && !v.isBlank()) return v;
    }
    return null;
  }

}
