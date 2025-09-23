package space.industock.industrial_stock.service.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.User;
import space.industock.industrial_stock.exception.UnauthorizedException;
import space.industock.industrial_stock.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User save(User user){
    return userRepository.save(user);
  }

  public User findById(UUID id){
    return userRepository.findById(id).orElseThrow(() -> new UnauthorizedException("Usuario não encontrado."));
  }

  public User findByName(String name){
    return userRepository.findByName(name).orElseThrow(() -> new UnauthorizedException("Usuario não encontrado."));
  }

  public User replace(UUID id, User user){
    user.setId(id);
    return this.save(user);
  }

  public List<User> findAll(){
    return userRepository.findAll();
  }

}
