package space.industock.industrial_stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import space.industock.industrial_stock.domain.utils.AddressDetails;
import space.industock.industrial_stock.enums.PaymentType;
import space.industock.industrial_stock.enums.Stage;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO {

  private Long id;

  @NotEmpty(message = "O nome precisa ser preenchido.")
  private String name;

  public boolean internalProduction = false;

  @NotNull(message = "Problemas ao preenchimento de formularios internos.")
  private AddressDetails address;

  @NotBlank(message = "O contato nao pode permanecer em branco.")
  private String phoneNumber;

  private List<ServiceOrderDTO> services;

  private PaymentType paymentType;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private boolean payment = false;

  @AssertTrue(message = "O contato deve ser informado.")
  public boolean isValidContact() {
    return internalProduction || (phoneNumber != null && !phoneNumber.isBlank());
  }

  private Stage stage;

  private List<Long> serviceIds;

  private Integer paymentTimes;
  private Integer paymentValueInCents;

}
