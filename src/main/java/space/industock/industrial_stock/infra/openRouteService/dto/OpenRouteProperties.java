package space.industock.industrial_stock.infra.openRouteService.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenRouteProperties {
  private String name;
  private String country;
  private String region;
  private String macroregion;
  private String locality;
  private String localadmin;
  private String borough;
  private String neighbourhood;
  private String street;
  private String postalcode;
  private String county;
}
