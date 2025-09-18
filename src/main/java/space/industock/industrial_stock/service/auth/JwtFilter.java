package space.industock.industrial_stock.service.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    if(authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);
      if(jwtUtil.isValid(token)){
        String email = jwtUtil.extractUsername(token);
        UserDetails user = userDetailsService.loadUserByUsername(email);

        try {
          isUserValid(user);
        } catch (Exception ex) {
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
          return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }

    filterChain.doFilter(request, response);
  }

  private void isUserValid(UserDetails user) {
    if (!user.isEnabled()) throw new DisabledException("Conta desativada");
    if (!user.isAccountNonLocked()) throw new LockedException("Conta bloqueada");
    if (!user.isAccountNonExpired()) throw new AccountExpiredException("Conta expirada");
    if (!user.isCredentialsNonExpired()) throw new CredentialsExpiredException("Senha expirada");
  }

}
