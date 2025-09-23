package space.industock.industrial_stock.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import space.industock.industrial_stock.domain.User;
import space.industock.industrial_stock.dto.user.UserGetDTO;
import space.industock.industrial_stock.dto.user.UserPostDTO;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

  @Mapping(target = "authorities", expression = "java(userPostDTO.getAuthorities().toString().substring(1, userPostDTO.getAuthorities().toString().length() - 1))")
  public abstract User toUser(UserPostDTO userPostDTO);

  //@Mapping(target = "authorities", expression = "java(user.getAuthorities().toString())")
  public abstract UserGetDTO toUserGetDTO(User user);

}
