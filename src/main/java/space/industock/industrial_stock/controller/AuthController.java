package space.industock.industrial_stock.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.domain.User;
import space.industock.industrial_stock.domain.utils.UserDetailsAdapter;
import space.industock.industrial_stock.dto.UserDTO;
import space.industock.industrial_stock.dto.auth.CurrentUser;
import space.industock.industrial_stock.dto.auth.LoginRequest;
import space.industock.industrial_stock.dto.auth.TokenResponse;
import space.industock.industrial_stock.service.UserService;
import space.industock.industrial_stock.service.auth.AuthService;
import space.industock.industrial_stock.service.auth.JwtUtil;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
@Validated
public class AuthController {

  private final AuthService authService;
  private final JwtUtil jwtUtil;
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest){
    TokenResponse login = authService.login(loginRequest);
    log.info(login.toString());
    return ResponseEntity.ok(login);
  }

  @GetMapping("/current")
  public ResponseEntity<CurrentUser> currentUser(@RequestHeader("Authorization") @Valid String token){
    if(jwtUtil.isTokenExpired(token.substring(7))){
      return ResponseEntity.ok(new CurrentUser(true, null, null));
    }

    UserDetailsAdapter authUser = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserDTO user = userService.toDto(authUser.getUser());

    return ResponseEntity.ok(new CurrentUser(jwtUtil.isTokenExpired(token.substring(7)), authUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(), user));
  }

  // debug
  @GetMapping("/current-user")
  public ResponseEntity<User> currentUserFull(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return ResponseEntity.ok(((UserDetailsAdapter) auth.getPrincipal()).getUser());
  }
}


































