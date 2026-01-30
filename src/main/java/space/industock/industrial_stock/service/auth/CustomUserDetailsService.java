package space.industock.industrial_stock.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.User;
import space.industock.industrial_stock.domain.utils.UserDetailsAdapter;
import space.industock.industrial_stock.service.PermissionService;
import space.industock.industrial_stock.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserService userService;
  private final PermissionService permissionService;

  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    User user = userService.findById(Long.parseLong(id));

    Set<String> permissions = permissionService.collectAllPermissions(user.getRole());

    Set<SimpleGrantedAuthority> authorities = permissions.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());

    return new UserDetailsAdapter(user, authorities);
  }



}
