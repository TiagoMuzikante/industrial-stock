package space.industock.industrial_stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.industock.industrial_stock.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
