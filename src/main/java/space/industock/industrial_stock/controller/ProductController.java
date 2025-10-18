package space.industock.industrial_stock.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.dto.ProductDTO;
import space.industock.industrial_stock.dto.productHistory.ProductHistoryDTO;
import space.industock.industrial_stock.service.ProductManagerService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

  private final ProductManagerService service;

  @PostMapping
  @PreAuthorize("hasAuthority('ADD_STOCK')")
  public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO){
    return new ResponseEntity<>(service.saveProduct(productDTO), HttpStatus.CREATED);
  }

  @PatchMapping("/update/{id}")
  @PreAuthorize("hasAuthority('ADD_STOCK')")
  public ResponseEntity<ProductDTO> replace(@RequestBody ProductDTO productDTO, @PathVariable Long id){
    return ResponseEntity.ok(service.replaceProduct(productDTO, id));
  }

  @GetMapping
  public ResponseEntity<List<ProductDTO>> findAll(){
    return ResponseEntity.ok(service.findAllProduct());
  }


  @PatchMapping("/increment/{id}")
  @PreAuthorize("hasAuthority('ADD_STOCK')")
  public ResponseEntity<ProductDTO> increment(@PathVariable Long id, @PathParam("amount") Integer amount){
    return ResponseEntity.ok(service.incrementProductAmount(id, amount));
  }

  @PreAuthorize("hasAuthority('REMOVE_STOCK')")
  @PatchMapping("/decrement/{id}")
  public ResponseEntity<ProductDTO> decrement(@PathVariable Long id, @PathParam("amount") Integer amount){
    return ResponseEntity.ok(service.decrementProductAmount(id, amount));
  }

  @PreAuthorize("hasAuthority('STOCK_DASHBOARD')")
  @GetMapping("/resume")
  public ResponseEntity<List<ProductHistoryDTO>> productHistoricDash(@RequestParam("order_by") String orderBy, @RequestParam("start_date") LocalDate startDate, @RequestParam("end_date") LocalDate endDate){
    return ResponseEntity.ok(service.findProductHistoric(orderBy, startDate, endDate));
  }


}
