package space.industock.industrial_stock.exceptionHandler.response;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {

  private int status;
  private String message;
  private Map<String, String> errors;

}
