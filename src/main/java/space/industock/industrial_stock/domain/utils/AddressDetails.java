package space.industock.industrial_stock.domain.utils;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AddressDetails {

  private String originalAddressLink;
  private String originalNeighborhood;

  private String streetNumber;
  private String route;
  private String neighborhood;
  private String city;
  private String state;
  private String country;
  private String postalCode;

  private Double lat;
  private Double lng;

  @Column(columnDefinition = "text")
  private String referenceDetails;

  @Override
  public String toString() {
    return String.format(
        "AddressDetails[route=%s, number=%s, neighborhood=%s, city=%s, state=%s, country=%s, cep=%s, lat=%f, lng=%f]",
        route, streetNumber, neighborhood, city, state, country, postalCode, lat, lng
    );
  }

}