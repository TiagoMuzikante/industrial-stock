package space.industock.industrial_stock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.dto.user.UserGetDTO;
import space.industock.industrial_stock.dto.user.UserPostDTO;
import space.industock.industrial_stock.service.UserManagerService;

@RestController
@CrossOrigin
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserManagerService service;

  @PostMapping("/new")
  public ResponseEntity<UserGetDTO> save(@RequestBody UserPostDTO userPostDTO){
    return new ResponseEntity<UserGetDTO>(service.saveUser(userPostDTO), HttpStatus.CREATED);
  }

}
