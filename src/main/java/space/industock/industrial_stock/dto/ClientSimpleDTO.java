package space.industock.industrial_stock.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import space.industock.industrial_stock.domain.utils.AddressDetails;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientSimpleDTO {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  @NotBlank(message = "O nome precisa ser preenchido.")
  private String name;

  @NotBlank(message = "O contato nao pode permanecer em branco.")
  private String phoneNumber;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private LocalDateTime createdAt;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private LocalDateTime finishedAt;

  private AddressDetails address;

}
