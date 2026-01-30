package space.industock.industrial_stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.Permission;
import space.industock.industrial_stock.domain.Role;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PermissionService {

  public Set<String> collectAllPermissions(Role role){
    return Stream.concat(
        role.getPermissions().stream().map(Permission::getName).collect(Collectors.toSet()).stream(),
        role.getChildren().stream().flatMap(child -> collectAllPermissions(child).stream()).collect(Collectors.toSet()).stream()
    ).collect(Collectors.toSet());

  }

}
