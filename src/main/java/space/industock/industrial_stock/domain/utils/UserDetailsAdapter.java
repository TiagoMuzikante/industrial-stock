package space.industock.industrial_stock.domain.utils;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import space.industock.industrial_stock.domain.User;

import java.util.Arrays;
import java.util.Collection;

@AllArgsConstructor
@Log4j2
public class UserDetailsAdapter implements UserDetails {

  private User user;

  public boolean isAccountNonExpired() {
    return user.getIsAccountNonExpired();
  }

  public boolean isAccountNonLocked() {
    return user.getIsAccountNonLocked();
  }

  public boolean isCredentialsNonExpired() {
    return user.getIsCredentialsNonExpired();
  }

  public boolean isEnabled() {
    return user.getIsEnable();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    log.info(user.getAuthorities());
    return Arrays.stream(user.getAuthorities().split(","))
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim()))
        .toList();
  }

  @Override
  public String getPassword() {
    return "";
  }

  @Override
  public String getUsername() {
    return user.getName();
  }

  public User getUser() {
    return user;
  }
}
