package space.industock.industrial_stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.User;
import space.industock.industrial_stock.dto.user.UserGetDTO;
import space.industock.industrial_stock.dto.user.UserPostDTO;
import space.industock.industrial_stock.mapper.UserMapper;
import space.industock.industrial_stock.service.domain.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserManagerService {

  private final UserService userService;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public UserGetDTO saveUser(UserPostDTO userPostDTO){
    User user = userMapper.toUser(userPostDTO);
    user.setRestartPassword(true);
    user.setIsEnable(true);
    user.setIsAccountNonLocked(true);
    user.setIsAccountNonExpired(true);
    user.setIsCredentialsNonExpired(true);
    return userMapper.toUserGetDTO(userService.save(user));
  }

  public void setPassword(User user, String password){
    user.setPassword(passwordEncoder.encode(password));
    user.setRestartPassword(false);
    userService.replace(user.getId(), user);
  }



}
