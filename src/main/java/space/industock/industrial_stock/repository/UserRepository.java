package space.industock.industrial_stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import space.industock.industrial_stock.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByEmail(String email);

  Optional<User> findByRegisterNumber(Integer register);

}
