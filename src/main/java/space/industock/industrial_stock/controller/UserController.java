package space.industock.industrial_stock.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.dto.UserDTO;
import space.industock.industrial_stock.service.UserService;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController extends BaseController<UserDTO> {

  private final UserService service;

  public UserController(UserService service) {
    super(service);
    this.service = service;
  }

  @PreAuthorize("hasAuthority('USER_MANAGER')")
  @GetMapping("/list-roles")
  public ResponseEntity<List<Map<Long, String>>> listAllRoles(){
    return ResponseEntity.ok(service.listAllRoles());
  }

  @PreAuthorize("hasAuthority('USER_MANAGER')")
  @PatchMapping("/reset-password/{id}")
  public ResponseEntity<Void> resetPassword(@PathVariable Long id){
    service.resetPassword(id);
    return ResponseEntity.ok().build();
  }

  @PreAuthorize("hasAuthority('USER_MANAGER')")
  @PatchMapping("/enable/{id}")
  public ResponseEntity<Void> enableUser(@PathVariable Long id){
    service.changeActive(id, true);
    return ResponseEntity.ok().build();
  }

  @PreAuthorize("hasAuthority('USER_MANAGER')")
  @PatchMapping("/disable/{id}")
  public ResponseEntity<Void> disableUser(@PathVariable Long id){
    service.changeActive(id, false);
    return ResponseEntity.ok().build();
  }

}
