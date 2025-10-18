package space.industock.industrial_stock.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import space.industock.industrial_stock.domain.Expense;
import space.industock.industrial_stock.dto.ExpenseDTO;

@Mapper(componentModel = "spring")
public abstract class ExpenseMapper {

  public abstract Expense ToExpense(ExpenseDTO expenseDTO);

  @Mapping(target = "userName", expression = "java(expense.getUser().getName())")
  public abstract ExpenseDTO toDto(Expense expense);

}
