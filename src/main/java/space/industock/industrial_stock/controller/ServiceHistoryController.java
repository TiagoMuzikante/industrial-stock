package space.industock.industrial_stock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.domain.utils.ImageBody;
import space.industock.industrial_stock.service.ServiceHistoryService;

@RestController
@RequestMapping("/service_history")
@CrossOrigin
@RequiredArgsConstructor
public class ServiceHistoryController {

  private final ServiceHistoryService service;

  @GetMapping("/images/{id}/{index}")
  public ResponseEntity<ImageBody> searchImagesByService(@PathVariable Long id, @PathVariable int index){
    return ResponseEntity.ok(service.findImageById(id, index));
  }

  @PostMapping("/images/{id}")
  public ResponseEntity<Void> saveImage(@PathVariable Long id, @RequestBody ImageBody image){
    return ResponseEntity.ok().build();
  }


}
