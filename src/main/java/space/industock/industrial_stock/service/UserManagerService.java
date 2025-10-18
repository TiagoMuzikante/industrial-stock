package space.industock.industrial_stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.User;
import space.industock.industrial_stock.dto.UserDTO;
import space.industock.industrial_stock.mapper.UserMapper;
import space.industock.industrial_stock.service.domain.UserService;

@Service
@RequiredArgsConstructor
public class UserManagerService {

  private final UserService userService;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public UserDTO saveUser(UserDTO userPostDTO){
    User user = userMapper.toUser(userPostDTO);
    user.setRestartPassword(true);
    user.setIsEnable(true);
    user.setIsAccountNonLocked(true);
    user.setIsAccountNonExpired(true);
    user.setIsCredentialsNonExpired(true);
    return userMapper.toDto(userService.save(user));
  }

  public void setPassword(User user, String password){
    user.setPassword(passwordEncoder.encode(password));
    user.setRestartPassword(false);
    userService.replace(user.getId(), user);
  }



}
