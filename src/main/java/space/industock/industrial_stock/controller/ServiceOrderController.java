package space.industock.industrial_stock.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.dto.ServiceOrderDTO;
import space.industock.industrial_stock.dto.routes.LngLat;
import space.industock.industrial_stock.service.ServiceOrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/services")
@Validated
@Log4j2
public class ServiceOrderController extends BaseController<ServiceOrderDTO> {

  private final ServiceOrderService service;

  public ServiceOrderController(ServiceOrderService service) {
    super(service);
    this.service = service;
  }

  @Override
  @GetMapping
  public ResponseEntity<List<ServiceOrderDTO>> findAll(){
    return ResponseEntity.ok(service.findAllOpenServices());
  }

  @PatchMapping("/job/end/{id}/{sessionId}")
  @PreAuthorize("hasAuthority('CONFIRM_PRODUCTION')")
  public ResponseEntity<ServiceOrderDTO> endJob(@PathVariable Long id, @PathVariable UUID sessionId){
    return ResponseEntity.ok(service.finishProduction(id, sessionId));
  }

  @PatchMapping("/deliver/end/{id}/{sessionId}")
  @PreAuthorize("hasAuthority('CONFIRM_DELIVER')")
  public ResponseEntity<ServiceOrderDTO> endDeliver(@PathVariable Long id, @RequestBody LngLat lngLat, @PathVariable UUID sessionId){
    return ResponseEntity.ok(service.finishDelivered(id, lngLat, sessionId));
  }

}
