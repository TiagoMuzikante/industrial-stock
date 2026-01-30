package space.industock.industrial_stock.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.domain.utils.ImageBody;
import space.industock.industrial_stock.dto.ServiceOrderDTO;
import space.industock.industrial_stock.dto.productHistory.ProductHistoryDTO;
import space.industock.industrial_stock.service.ProductHistoricService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/product_historic")
public class ProductHistoricController extends BaseController<ProductHistoryDTO> {

  private final ProductHistoricService service;

  public ProductHistoricController(ProductHistoricService service) {
    super(service);
    this.service = service;
  }


  @PreAuthorize("hasAuthority('STOCK_DASHBOARD')")
  @GetMapping("/resume")
  public ResponseEntity<List<ProductHistoryDTO>> productHistoricDash(@RequestParam("order_by") String orderBy, @RequestParam("start_date") LocalDate startDate, @RequestParam("end_date") LocalDate endDate){
    return ResponseEntity.ok(service.findHistoricBetweenDates(orderBy, startDate, endDate));
  }

}
