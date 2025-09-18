package space.industock.industrial_stock.service.domain;

import lombok.RequiredArgsConstructor;
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

  public User save(User user){
    return userRepository.save(user);
  }

  public User findById(UUID id){
    return userRepository.findById(id).orElseThrow(() -> new UnauthorizedException("Usuario não encontrado."));
  }

  public User findByEmail(String email){
    return userRepository.findByEmail(email).orElseThrow(() -> new UnauthorizedException("Usuario não encontrado."));
  }

  public List<User> findAll(){
    return userRepository.findAll();
  }

}
