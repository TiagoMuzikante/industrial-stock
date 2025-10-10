package space.industock.industrial_stock.domain.utils;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import space.industock.industrial_stock.domain.User;

import java.util.Collection;
import java.util.Set;

@NoArgsConstructor
public class UserDetailsAdapter implements UserDetails {

  private User user;
  private Set<SimpleGrantedAuthority> authorities;

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
    return this.authorities;
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

  public UserDetailsAdapter(User user, Set<SimpleGrantedAuthority> authorities) {
    this.user = user;
    this.authorities = authorities;
  }
}
