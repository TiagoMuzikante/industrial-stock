package space.industock.industrial_stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.industock.industrial_stock.domain.Expense;
import space.industock.industrial_stock.enums.ExpenseType;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

  List<Expense> findAllByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(LocalDateTime createdAt, LocalDateTime createdAt2);

  List<Expense> findAllByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqualAndTypeEquals(LocalDateTime createdAt, LocalDateTime createdAt2, ExpenseType type);

}
