package space.industock.industrial_stock.repository;

import org.springframework.data.jpa.repository.Query;
import space.industock.industrial_stock.domain.ProductHistoric;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductHistoricRepository extends BaseRepository<ProductHistoric, Long> {


  @Query("""
    SELECT e FROM ProductHistoric e
    WHERE e.createdAt >= :startDate AND e.createdAt <= :endDate
""")
  List<ProductHistoric> findAllBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

}
