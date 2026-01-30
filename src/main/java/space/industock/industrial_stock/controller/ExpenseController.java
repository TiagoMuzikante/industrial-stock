package space.industock.industrial_stock.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.dto.ExpenseDTO;
import space.industock.industrial_stock.service.ExpenseService;

import java.time.YearMonth;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/expense")
@Validated
public class ExpenseController extends BaseController<ExpenseDTO>{

  private final ExpenseService service;

  public ExpenseController(ExpenseService service) {
    super(service);
    this.service = service;
  }

  @Override
  @PostMapping
  @PreAuthorize("hasAuthority('ADD_EXPENSE')")
  public ResponseEntity<ExpenseDTO> save(@RequestBody @Valid ExpenseDTO expenseDTO){
    return super.save(expenseDTO);
  }

  @GetMapping("/{month}/{year}")
  @PreAuthorize("hasAuthority('EXPENSE_DASHBOARD')")
  public ResponseEntity<List<ExpenseDTO>> findByMonth(@PathVariable(required = true) @Min(2025) int year, @PathVariable(required = true) @Min(1) @Max(12) int month){
    return ResponseEntity.ok(service.findAllByYearMonth(YearMonth.of(year,month)));
  }


}
