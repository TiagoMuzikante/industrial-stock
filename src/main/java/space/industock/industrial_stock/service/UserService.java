package space.industock.industrial_stock.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.Role;
import space.industock.industrial_stock.domain.User;
import space.industock.industrial_stock.dto.UserDTO;
import space.industock.industrial_stock.exception.InternalAuthorizedException;
import space.industock.industrial_stock.exception.UnauthorizedException;
import space.industock.industrial_stock.repository.RoleRepository;
import space.industock.industrial_stock.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService<User, UserDTO> {

  private final UserRepository repository;
  private final PasswordEncoder encoder;
  private final RoleRepository roleRepository;

  public UserService(UserRepository repository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
    super(repository);
    this.repository = repository;
    this.encoder = passwordEncoder;
    this.roleRepository = roleRepository;
  }

  public UserDTO save(UserDTO dto){
    dto.setId(null);
    return super.save(dto);
  }

  public User findByName(String name){
    return repository.findByName(name).orElseThrow(() -> new UnauthorizedException("Usuario não encontrado."));
  }

  public void setPassword(User user, String password){
    user.setPassword(encoder.encode(password));
    user.setRestartPassword(false);
    repository.save(user);
  }

  public List<Map<Long, String>> listAllRoles(){
    return roleRepository.findAll().stream().map(role -> Map.of(role.getId(), role.getName())).toList();
  }

  public void resetPassword(Long id){
    User user = repository.findById(id).orElseThrow(() -> new InternalAuthorizedException(" Usuario não encontrado"));
    user.setRestartPassword(true);
    repository.save(user);
  }

  public void changeActive(Long id, boolean enable){
    User user = repository.findById(id).orElseThrow(() -> new InternalAuthorizedException(" Usuario não encontrado"));
    user.setEnable(enable);
    repository.save(user);
  }

}
