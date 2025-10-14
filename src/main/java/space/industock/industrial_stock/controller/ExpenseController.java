package space.industock.industrial_stock.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.dto.expense.ExpenseGetDTO;
import space.industock.industrial_stock.dto.expense.ExpensePostDTO;
import space.industock.industrial_stock.enums.ExpenseType;
import space.industock.industrial_stock.service.ExpenseServiceManager;

import java.time.YearMonth;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/expense")
@RequiredArgsConstructor
@Validated
public class ExpenseController {

  private final ExpenseServiceManager expenseServiceManager;

  @PostMapping
  @PreAuthorize("hasAuthority('ADD_EXPENSE')")
  public ResponseEntity<ExpenseGetDTO> save(@RequestBody @Valid ExpensePostDTO expensePostDTO){
    return new ResponseEntity<>(expenseServiceManager.save(expensePostDTO), HttpStatus.CREATED);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('EXPENSE_DASHBOARD')")
  public ResponseEntity<List<ExpenseGetDTO>> findByMonth(@RequestParam(required = true) @Min(2025) int year, @RequestParam(required = true) @Min(1) @Max(12) int month,@RequestParam(required = false) ExpenseType type){
    return ResponseEntity.ok(expenseServiceManager.findAllByYearMonthAndType(YearMonth.of(year,month), type));
  }


}
