package space.industock.industrial_stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.industock.industrial_stock.domain.ProductHistoric;

public interface ProductHistoricRepository extends JpaRepository<ProductHistoric, Long> {
}
