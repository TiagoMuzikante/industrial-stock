package space.industock.industrial_stock.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.dto.ClientDTO;
import space.industock.industrial_stock.dto.ClientSimpleDTO;
import space.industock.industrial_stock.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController extends BaseController<ClientDTO> {

  private ClientService service;

  public ClientController(ClientService service) {
    super(service);
    this.service = service;
  }

  @GetMapping("/show/{id}")
  @PreAuthorize("hasAuthority('CLIENT_VIEW')")
  public ResponseEntity<ClientDTO> findClientById(@PathVariable Long id){
    return ResponseEntity.ok(service.findClientById(id));
  }

  @PreAuthorize("hasAuthority('CLIENT_MANAGER')")
  @PostMapping("/simple")
  public ResponseEntity<ClientSimpleDTO> save(@RequestBody @Valid ClientSimpleDTO ClientDto){
    return new ResponseEntity<>(service.save(ClientDto), HttpStatus.CREATED);
  }

  @PreAuthorize("hasAuthority('CLIENT_MANAGER')")
  @GetMapping("/simple")
  public ResponseEntity<List<ClientSimpleDTO>> findAllSimple(){
    return ResponseEntity.ok(service.findAllSimple());
  }

  @PreAuthorize("hasAuthority('CLIENT_MANAGER')")
  @GetMapping("/simple/{id}")
  public ResponseEntity<ClientSimpleDTO> findSimpleById(@PathVariable Long id){
    return ResponseEntity.ok(service.findSimpleById(id));
  }

  @PreAuthorize("hasAuthority('CLIENT_MANAGER')")
  @PutMapping("/simple/{id}")
  public ResponseEntity<ClientSimpleDTO> simpleUpdate(@PathVariable Long id, @RequestBody ClientSimpleDTO dto){
    return ResponseEntity.ok(service.update(dto, id));
  }

  @PutMapping("/{id}")
  @Override
  public ResponseEntity<ClientDTO> replace(@PathVariable Long id, @RequestBody @Valid ClientDTO dto){
    return ResponseEntity.ok(service.replace(id, dto));
  }

  @PutMapping("/{id}/service/")
  public ResponseEntity<ClientDTO> replaceWithService(@PathVariable Long id, @RequestBody ClientDTO dto){
    return ResponseEntity.ok(service.replaceWithService(id, dto));
  }

  @PreAuthorize("hasAuthority('CLIENT_MANAGER')")
  @PatchMapping("/finish/{id}")
  public ResponseEntity<Void> finish(@PathVariable Long id){
    service.finishClient(id);
    return ResponseEntity.noContent().build();
  }



}
