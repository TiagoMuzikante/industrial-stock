package space.industock.industrial_stock.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.Expense;
import space.industock.industrial_stock.domain.utils.UserDetailsAdapter;
import space.industock.industrial_stock.dto.ExpenseDTO;
import space.industock.industrial_stock.repository.ExpenseRepository;

import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class ExpenseService extends BaseService<Expense, ExpenseDTO> {

  private final ExpenseRepository repository;

  public ExpenseService(ExpenseRepository repository) {
    super(repository);
    this.repository = repository;
  }

  public ExpenseDTO save(ExpenseDTO expenseDTO){
    Expense expense = super.toEntity(expenseDTO);
    expense.setUser(((UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

    return super.toDto(repository.save(expense));
  }

  public List<ExpenseDTO> findAllByYearMonth(YearMonth yearMonth){
    return repository.findAllByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(
        yearMonth.atEndOfMonth().atTime(LocalTime.MAX),
        yearMonth.atDay(1).atStartOfDay()
    ).stream().map(super::toDto).toList();
  }

}
