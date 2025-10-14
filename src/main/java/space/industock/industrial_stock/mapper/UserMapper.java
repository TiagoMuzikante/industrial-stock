package space.industock.industrial_stock.mapper;

import org.mapstruct.Mapper;
import space.industock.industrial_stock.domain.User;
import space.industock.industrial_stock.dto.user.UserGetDTO;
import space.industock.industrial_stock.dto.user.UserPostDTO;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

  public abstract User toUser(UserPostDTO userPostDTO);

  public abstract UserGetDTO toUserGetDTO(User user);

}
