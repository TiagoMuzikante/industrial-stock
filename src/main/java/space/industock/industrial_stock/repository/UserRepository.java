package space.industock.industrial_stock.repository;

import space.industock.industrial_stock.domain.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {

  Optional<User> findByName(String name);

}
