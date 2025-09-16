package space.industock.industrial_stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.industock.industrial_stock.domain.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
