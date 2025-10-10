package space.industock.industrial_stock.exception;

import org.springframework.security.core.AuthenticationException;

public class InternalAuthorizedException extends AuthenticationException {
  public InternalAuthorizedException(String message) {
    super(message);
  }
}
