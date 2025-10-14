package space.industock.industrial_stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.Expense;
import space.industock.industrial_stock.domain.utils.UserDetailsAdapter;
import space.industock.industrial_stock.dto.expense.ExpenseGetDTO;
import space.industock.industrial_stock.dto.expense.ExpensePostDTO;
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

  public ExpenseGetDTO save(ExpensePostDTO expensePostDTO){
    Expense expense = expenseMapper.ToExpense(expensePostDTO);
    expense.setUser(((UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

    return expenseMapper.toExpenseGetDTO(expenseService.save(expense));
  }

  public List<ExpenseGetDTO> findAllByYearMonthAndType(YearMonth yearMonth, ExpenseType type){
    if(type != null)
      return expenseService.findAllByYearMonthAndType(yearMonth, type).stream().map(expenseMapper::toExpenseGetDTO).toList();
    return expenseService.findAllByYearMonth(yearMonth).stream().map(expenseMapper::toExpenseGetDTO).toList();
  }

  public ExpenseGetDTO findById(Long id){
    return expenseMapper.toExpenseGetDTO(expenseService.findById(id));
  }

}
