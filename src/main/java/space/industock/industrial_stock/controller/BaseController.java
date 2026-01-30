package space.industock.industrial_stock.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.domain.BaseEntity;
import space.industock.industrial_stock.service.BaseService;

import java.util.List;

public abstract class BaseController<D> {

  protected final BaseService<? extends BaseEntity, D> service;

  protected BaseController(BaseService<? extends BaseEntity, D> service){
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<D> save(@RequestBody @Valid D d){
    return new ResponseEntity<>(service.save(d), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<D>> findAll(){
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<D> findById(@PathVariable Long id){
    return ResponseEntity.ok(service.find(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteByID(@PathVariable Long id){
    service.destroy(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<D> replace(@PathVariable Long id, @RequestBody @Valid D d){
    return ResponseEntity.ok(service.replace(id, d));
  }

}
