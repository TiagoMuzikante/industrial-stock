package space.industock.industrial_stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.dto.user.UserGetDTO;
import space.industock.industrial_stock.dto.user.UserPostDTO;
import space.industock.industrial_stock.mapper.UserMapper;
import space.industock.industrial_stock.service.domain.UserService;

@Service
@RequiredArgsConstructor
public class UserManagerService {

  private UserService userService;
  private UserMapper userMapper;


  public UserGetDTO saveUser(UserPostDTO userPostDTO){
    return userMapper.toUserGetDTO(userService.save(userMapper.toUser(userPostDTO)));
  }



}
