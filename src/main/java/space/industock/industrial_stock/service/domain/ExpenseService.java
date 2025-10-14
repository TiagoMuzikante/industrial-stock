package space.industock.industrial_stock.service.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.Expense;
import space.industock.industrial_stock.enums.ExpenseType;
import space.industock.industrial_stock.exception.InternalAuthorizedException;
import space.industock.industrial_stock.repository.ExpenseRepository;

import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

  private final ExpenseRepository expenseRepository;

  public Expense save(Expense expense){
    return expenseRepository.save(expense);
  }

  public List<Expense> findAllByYearMonthAndType(YearMonth yearMonth, ExpenseType type){
    return expenseRepository.findAllByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqualAndTypeEquals(
        yearMonth.atEndOfMonth().atTime(LocalTime.MAX),
        yearMonth.atDay(1).atStartOfDay(),
        type
    );
  }

  public List<Expense> findAllByYearMonth(YearMonth yearMonth){
    return expenseRepository.findAllByCreatedAtLessThanEqualAndCreatedAtGreaterThanEqual(
        yearMonth.atEndOfMonth().atTime(LocalTime.MAX),
        yearMonth.atDay(1).atStartOfDay()
    );
  }

  public Expense findById(Long id){
    return expenseRepository.findById(id).orElseThrow(() -> new InternalAuthorizedException("Registro não encontrado."));
  }

  public void destroyById(Long id){
    expenseRepository.deleteById(id);
  }


}
