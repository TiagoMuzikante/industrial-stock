package space.industock.industrial_stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.Expense;
import space.industock.industrial_stock.domain.utils.UserDetailsAdapter;
import space.industock.industrial_stock.dto.ExpenseDTO;
import space.industock.industrial_stock.enums.ExpenseType;
import space.industock.industrial_stock.mapper.ExpenseMapper;
import space.industock.industrial_stock.service.domain.ExpenseService;

import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceManager {


  private final ExpenseService expenseService;
  private final ExpenseMapper expenseMapper;

  public ExpenseDTO save(ExpenseDTO expenseDTO){
    Expense expense = expenseMapper.ToExpense(expenseDTO);
    expense.setUser(((UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

    return expenseMapper.toDto(expenseService.save(expense));
  }

  public List<ExpenseDTO> findAllByYearMonth(YearMonth yearMonth){
    return expenseService.findAllByYearMonth(yearMonth).stream().map(expenseMapper::toDto).toList();
  }

  public ExpenseDTO findById(Long id){
    return expenseMapper.toDto(expenseService.findById(id));
  }

}
