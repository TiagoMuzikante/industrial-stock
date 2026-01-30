package space.industock.industrial_stock.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.dto.QueueItemDTO;
import space.industock.industrial_stock.enums.Stage;
import space.industock.industrial_stock.service.QueueService;

import java.util.List;

@RestController
@RequestMapping("/queue")
@RequiredArgsConstructor
@CrossOrigin
@Log4j2
public class QueueController {

  private final QueueService service;

  @GetMapping("/count/{stage}")
  public ResponseEntity<Long> count(@PathVariable Stage stage){
    return ResponseEntity.ok(service.countByStage(stage));
  }

  @GetMapping("/list/{stage}")
  @PreAuthorize("hasAuthority('QUEUE_LIST')")
  public ResponseEntity<List<QueueItemDTO>> listAllPending(@PathVariable Stage stage){
    return ResponseEntity.ok(service.getQueue(stage));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('QUEUE_LIST')")
  public ResponseEntity<QueueItemDTO> listServicesByClientStage(@PathVariable Long id){
    return ResponseEntity.ok(service.findItem(id));
  }

//  @PreAuthorize("hasAuthority('CONFIRM_NEXT')")
//  @PatchMapping("/confirm/stage/{id}")
//  public ResponseEntity<Void> confirmToNext(@PathVariable Long id){
//    service.moveToNextStage(id);
//    return ResponseEntity.ok().build();
//  }

  @PreAuthorize("hasAuthority('CURRENT_PRODUCTION')")
  @GetMapping("/job/current")
  public ResponseEntity<QueueItemDTO> getCurrentProduction(){
    return ResponseEntity.ok(service.CurrentProduction());
  }


  @PreAuthorize("hasAuthority('CONFIRM_PRODUCTION')")
  @PatchMapping("/job/start/{id}")
  public ResponseEntity<Void> startJob(@PathVariable Long id){
    service.startProduction(id);
    return ResponseEntity.ok().build();
  }

}
