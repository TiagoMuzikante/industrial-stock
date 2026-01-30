package space.industock.industrial_stock.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.dto.ProductDTO;
import space.industock.industrial_stock.service.ProductService;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController extends BaseController<ProductDTO>{

  private final ProductService service;

  public ProductController(ProductService service) {
    super(service);
    this.service = service;
  }

  @Override
  @PostMapping
  @PreAuthorize("hasAuthority('ADD_STOCK')")
  public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO){
    return super.save(productDTO);
  }

  @PatchMapping("/update/{id}")
  @PreAuthorize("hasAuthority('ADD_STOCK')")
  public ResponseEntity<ProductDTO> replace(@RequestBody ProductDTO productDTO, @PathVariable Long id){
    return super.replace(id, productDTO);
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

}
