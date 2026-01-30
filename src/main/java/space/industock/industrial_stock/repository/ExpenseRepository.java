package space.industock.industrial_stock.repository;

import space.industock.industrial_stock.domain.Expense;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends BaseRepository<Expense, Long> {

  List<Expense> findAllByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(LocalDateTime createdAt, LocalDateTime createdAt2);

}
