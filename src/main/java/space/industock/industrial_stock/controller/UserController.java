package space.industock.industrial_stock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.dto.UserDTO;
import space.industock.industrial_stock.service.UserManagerService;

@RestController
@CrossOrigin
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserManagerService service;

  @PostMapping("/new")
  public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO){
    return new ResponseEntity<UserDTO>(service.saveUser(userDTO), HttpStatus.CREATED);
  }

}
