package space.industock.industrial_stock.exceptionHandler.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

  private int status;
  private String message;
  private String path;
  private LocalDateTime timestamp;

  public ErrorResponse(int status, String message, String path){
    this.status = status;
    this.message = message;
    this.path = path;
    this.timestamp = LocalDateTime.now();
  }

}
