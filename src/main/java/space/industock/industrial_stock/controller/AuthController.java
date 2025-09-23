package space.industock.industrial_stock.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.domain.utils.UserDetailsAdapter;
import space.industock.industrial_stock.dto.auth.LoginRequest;
import space.industock.industrial_stock.dto.auth.TokenResponse;
import space.industock.industrial_stock.dto.user.UserGetDTO;
import space.industock.industrial_stock.mapper.UserMapper;
import space.industock.industrial_stock.repository.UserRepository;
import space.industock.industrial_stock.service.auth.JwtUtil;
import space.industock.industrial_stock.service.domain.AuthService;
import space.industock.industrial_stock.service.domain.UserService;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

  private final AuthService authService;
  private final JwtUtil jwtUtil;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest){
    log.info(loginRequest.toString());

    return ResponseEntity.ok(authService.login(loginRequest));
  }

  @PostMapping("/new")
  public ResponseEntity<Void> createUser(){
    //userRepository.save(new User( null, "minerator", "lrs_admin@industock.space", "123.456.789-12", passwordEncoder.encode("!pH6@gTsO9$dsxB"), null, true, true, true, true, "ADMIN"));
    return ResponseEntity.ok().build();
  }

  @GetMapping("/current")
  public ResponseEntity<UserGetDTO> currentUser(@RequestHeader("Authorization") String token){
//  Faker faker = new Faker(new Locale("pt-BR"));
//
//    for(int i =0; i<=100; i++){
//      Product product = new Product();
//      product.setName(faker.name().title());
//      product.setAvailableAmount(faker.number().numberBetween(10, 40));
//      product.setMinimumAmount(faker.number().numberBetween(0, 19));
//      productRepository.save(product);
//    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    return ResponseEntity.ok(userMapper.toUserGetDTO(((UserDetailsAdapter) auth.getPrincipal()).getUser()));
  }

}


































