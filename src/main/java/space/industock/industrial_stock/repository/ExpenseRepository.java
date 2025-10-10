package space.industock.industrial_stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.industock.industrial_stock.domain.Expense;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  List<Expense> findAllByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(LocalDateTime endDate, LocalDateTime startDate);

}
