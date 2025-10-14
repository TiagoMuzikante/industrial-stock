package space.industock.industrial_stock.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyAuthoritiesMapper;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import space.industock.industrial_stock.exceptionHandler.response.ErrorResponse;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final CustomUserDetailsService userDetailsService;
  private final ObjectMapper objectMapper;
  private final RoleHierarchy hierarchy;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    try {
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);

        if (!jwtUtil.isValid(token)) {
          throw new SecurityException("Acesso negado: Token invalido ou expirado.");
        }
        String userId = jwtUtil.extractUserId(token);
        UserDetails user = userDetailsService.loadUserByUsername(userId);

        isUserValid(user);

        RoleHierarchyAuthoritiesMapper mapper = new RoleHierarchyAuthoritiesMapper(hierarchy);


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, mapper.mapAuthorities(user.getAuthorities()));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }

      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException ex){
      log.error(ex);
      writeResponseError(response, HttpStatus.UNAUTHORIZED, "Acesso negado: Token invalido ou expirado.");
    } catch (UsernameNotFoundException ex) {
      writeResponseError(response, HttpStatus.UNAUTHORIZED, "Acesso negado: Usuario não encontrado");
    } catch (SecurityException ex){
      writeResponseError(response, HttpStatus.UNAUTHORIZED, ex.getMessage());
    } catch (Exception ex) {
      ex.printStackTrace();
      writeResponseError(response, HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor.");
    }
  }

  private void writeResponseError(HttpServletResponse response, HttpStatus status, String message) throws IOException {
    response.setContentType("application/json");
    response.setStatus(status.value());
    response.setCharacterEncoding("UTF-8");

    ErrorResponse errorResponse = new ErrorResponse(status.value(), message,
        ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

    objectMapper.writeValue(response.getWriter(), errorResponse);
  }



  private void isUserValid(UserDetails user) {
    if (!user.isEnabled()) throw new DisabledException("Conta desativada");
    if (!user.isAccountNonLocked()) throw new LockedException("Conta bloqueada");
    if (!user.isAccountNonExpired()) throw new AccountExpiredException("Conta expirada");
    if (!user.isCredentialsNonExpired()) throw new CredentialsExpiredException("Senha expirada");
  }

}
