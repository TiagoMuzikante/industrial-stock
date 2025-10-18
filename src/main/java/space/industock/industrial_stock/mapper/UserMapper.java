package space.industock.industrial_stock.mapper;

import org.mapstruct.Mapper;
import space.industock.industrial_stock.domain.User;
import space.industock.industrial_stock.dto.UserDTO;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

  public abstract User toUser(UserDTO userDTO);

  public abstract UserDTO toDto(User user);

}
