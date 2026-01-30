package space.industock.industrial_stock.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.dto.QueueItemDTO;
import space.industock.industrial_stock.dto.ServiceOrderDTO;
import space.industock.industrial_stock.dto.routes.LngLat;
import space.industock.industrial_stock.service.ServiceOrderService;

import java.util.List;

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

  @PatchMapping("/job/end/{id}")
  @PreAuthorize("hasAuthority('CONFIRM_PRODUCTION')")
  public ResponseEntity<ServiceOrderDTO> endJob(@PathVariable Long id){
    return ResponseEntity.ok(service.finishProduction(id));
  }

  @PatchMapping("/deliver/end/{id}")
  @PreAuthorize("hasAuthority('CONFIRM_DELIVER')")
  public ResponseEntity<ServiceOrderDTO> endDeliver(@PathVariable Long id, @RequestBody LngLat lngLat){
    return ResponseEntity.ok(service.finishDelivered(id, lngLat));
  }

}
