package space.industock.industrial_stock.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.utils.UserDetailsAdapter;
import space.industock.industrial_stock.service.domain.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    return new UserDetailsAdapter(userService.findById(UUID.fromString(id)));
  }



}
