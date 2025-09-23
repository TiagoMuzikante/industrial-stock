package space.industock.industrial_stock.service.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.User;
import space.industock.industrial_stock.dto.auth.LoginRequest;
import space.industock.industrial_stock.dto.auth.TokenResponse;
import space.industock.industrial_stock.dto.user.UserGetDTO;
import space.industock.industrial_stock.exception.UnauthorizedException;
import space.industock.industrial_stock.mapper.UserMapper;
import space.industock.industrial_stock.repository.UserRepository;
import space.industock.industrial_stock.service.UserManagerService;
import space.industock.industrial_stock.service.auth.JwtUtil;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;
  private final JwtUtil jwtUtil;
  private final UserService userService;
  private final UserManagerService userManagerService;
  @Value("${jwt.secretClient}")
  private String frontToken;

  public TokenResponse login(LoginRequest request){
    User user = userRepository.findByName(request.getName()).orElseThrow(() -> new UnauthorizedException("Usuario nao encontrado"));

    if(user.getRestartPassword()){
      userManagerService.setPassword(user, request.getPassword());
    }

    if(request.getFrontSecret() == null ||
        !passwordEncoder.matches(request.getPassword(), user.getPassword()) ||
        !frontToken.equals(request.getFrontSecret())){

      throw new UnauthorizedException("Credenciais invalidas");
    }

    String token = jwtUtil.generateToken(user.getId());
    return new TokenResponse(token, userMapper.toUserGetDTO(user));
  }

  public UserGetDTO findById(String id){
    return userMapper.toUserGetDTO(userService.findById(UUID.fromString(id)));
  }

}
