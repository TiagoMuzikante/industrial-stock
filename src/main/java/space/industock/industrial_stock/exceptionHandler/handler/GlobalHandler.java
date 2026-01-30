package space.industock.industrial_stock.exceptionHandler.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import space.industock.industrial_stock.exception.GeoCodeNotValidException;
import space.industock.industrial_stock.exceptionHandler.response.ErrorResponse;
import space.industock.industrial_stock.exceptionHandler.response.ValidationErrorResponse;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalHandler {

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> handlerNotFound(NoHandlerFoundException ex){
    return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Rota não encontrada.", ex.getRequestURL()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(GeoCodeNotValidException.class)
  public ResponseEntity<ErrorResponse> geoCodeNotValidException(GeoCodeNotValidException ex){
    return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorResponse> validationErrorHandler(MethodArgumentNotValidException ex){
    return new ResponseEntity<>(
        new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(),"Erro de validação",
            ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(
                FieldError::getField,
                FieldError::getDefaultMessage
            ))), HttpStatus.BAD_REQUEST);
}


}
