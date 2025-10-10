package space.industock.industrial_stock.exceptionHandler.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;

@Data
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
