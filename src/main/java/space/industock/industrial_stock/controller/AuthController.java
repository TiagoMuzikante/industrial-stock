package space.industock.industrial_stock.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.domain.User;
import space.industock.industrial_stock.domain.utils.UserDetailsAdapter;
import space.industock.industrial_stock.dto.auth.CurrentUser;
import space.industock.industrial_stock.dto.auth.LoginRequest;
import space.industock.industrial_stock.dto.auth.TokenResponse;
import space.industock.industrial_stock.dto.user.UserGetDTO;
import space.industock.industrial_stock.mapper.UserMapper;
import space.industock.industrial_stock.repository.RoleRepository;
import space.industock.industrial_stock.repository.UserRepository;
import space.industock.industrial_stock.service.auth.JwtUtil;
import space.industock.industrial_stock.service.domain.AuthService;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
@Validated
public class AuthController {

  private final AuthService authService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final UserMapper userMapper;
  private final JwtUtil jwtUtil;

  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest){
    log.info(loginRequest.toString());

    return ResponseEntity.ok(authService.login(loginRequest));
  }

  @PostMapping("/new-default-user")
  public ResponseEntity<Void> createUser(){
   // userRepository.save(new User(
   //     null,
   //     "minerator",
   //     passwordEncoder.encode("!pH6@gTsO9$dsxB"),
   //     false,
   //     true,
   //     true,
   //     true,
   //     true,
   //     null,
   //     null,
   //     roleRepository.findByName("ROLE_OWNER").orElseThrow()
   // ));
    return ResponseEntity.ok().build();
  }

  @GetMapping("/current")
  public ResponseEntity<CurrentUser> currentUser(@RequestHeader("Authorization") @Valid String token){
    if(jwtUtil.isTokenExpired(token.substring(7))){
      return ResponseEntity.ok(new CurrentUser(true, null, null));
    }

    UserDetailsAdapter authUser = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserGetDTO user = userMapper.toUserGetDTO(authUser.getUser());

    return ResponseEntity.ok(new CurrentUser(jwtUtil.isTokenExpired(token.substring(7)), authUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(), user));
  }

  // debug
  @GetMapping("/current-user")
  public ResponseEntity<User> currentUserFull(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return ResponseEntity.ok(((UserDetailsAdapter) auth.getPrincipal()).getUser());
  }
}


































