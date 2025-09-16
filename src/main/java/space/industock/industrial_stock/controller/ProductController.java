package space.industock.industrial_stock.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.dto.product.ProductGetDTO;
import space.industock.industrial_stock.dto.product.ProductPostDTO;
import space.industock.industrial_stock.service.ProductManagerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produtos")
@CrossOrigin
public class ProductController {

  private ProductManagerService service;

  @PostMapping
  public ResponseEntity<ProductGetDTO> save(@RequestBody ProductPostDTO productPostDTO){
    return new ResponseEntity<>(service.saveProduct(productPostDTO), HttpStatus.CREATED);
  }

  @PatchMapping("/update/{id}")
  public ResponseEntity<ProductGetDTO> replace(@RequestBody ProductPostDTO productPostDTO, @PathVariable Long id){
    return ResponseEntity.ok(service.replaceProduct(productPostDTO, id));
  }

  @GetMapping
  public ResponseEntity<List<ProductGetDTO>> findAll(){
    return ResponseEntity.ok(service.findAllProduct());
  }

  @PatchMapping("/increment/{id}")
  public ResponseEntity<ProductGetDTO> increment(@PathVariable Long id, @PathParam("amount") Integer amount){
    return ResponseEntity.ok(service.incrementProductAmount(id, amount));
  }

  @PatchMapping("/decrement/{id}")
  public ResponseEntity<ProductGetDTO> decrement(@PathVariable Long id, @PathParam("amount") Integer amount){
    return ResponseEntity.ok(service.decrementProductAmount(id, amount));
  }





}
