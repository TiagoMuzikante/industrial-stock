package space.industock.industrial_stock.exceptionHandler.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse {

  private int status;
  private String message;
  private Map<String, String> errors;

}
