package space.industock.industrial_stock.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import space.industock.industrial_stock.domain.Expense;
import space.industock.industrial_stock.dto.expense.ExpenseGetDTO;
import space.industock.industrial_stock.dto.expense.ExpensePostDTO;

@Mapper(componentModel = "spring")
public abstract class ExpenseMapper {

  public abstract Expense ToExpense(ExpensePostDTO expensePostDTO);

  @Mapping(target = "userName", expression = "java(expense.getUser().getName())")
  public abstract ExpenseGetDTO toExpenseGetDTO(Expense expense);

}
