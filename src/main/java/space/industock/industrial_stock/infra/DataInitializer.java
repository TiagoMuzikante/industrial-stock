package space.industock.industrial_stock.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import space.industock.industrial_stock.domain.Permission;
import space.industock.industrial_stock.domain.Role;
import space.industock.industrial_stock.enums.RoleEnum;
import space.industock.industrial_stock.enums.permissions.Manager;
import space.industock.industrial_stock.enums.permissions.Owner;
import space.industock.industrial_stock.enums.permissions.Worker;
import space.industock.industrial_stock.repository.PermissionRepository;
import space.industock.industrial_stock.repository.RoleRepository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;

  @Override
  @Transactional
  public void run(String... args) throws Exception {

    // Map que associa cada RoleEnum a um enum de permissões
    Map<RoleEnum, Class<? extends Enum<?>>> roleReferences = Map.of(
      RoleEnum.WORKER, Worker.class,
      RoleEnum.MANAGER, Manager.class,
      RoleEnum.OWNER, Owner.class
    );

    Map<RoleEnum, Role> roleMap = new LinkedHashMap<>();

    // Criar/atualizar roles
    for (RoleEnum re : RoleEnum.values()) {
      String roleName = "ROLE_" + re.name();
      Role role = roleRepository.findByName(roleName).orElse(new Role(roleName));

      // 3️⃣ Obter enum de permissões correspondente
      Class<? extends Enum<?>> permEnumClass = roleReferences.get(re);
      if (permEnumClass != null) {
        for (Enum<?> permEnumValue : permEnumClass.getEnumConstants()) {
          String permName = permEnumValue.name();
          Permission permission = permissionRepository.findByName(permName)
              .orElseGet(() -> permissionRepository.save(new Permission(null, permName)));
          role.getPermissions().add(permission);
        }
      }

      roleRepository.save(role);
      roleMap.put(re, role);
    }

    // Atualizar hierarquia (cada role herda a anterior na ordem do enum)
    Role previousRole = null;
    for (RoleEnum re : RoleEnum.values()) {
      Role currentRole = roleMap.get(re);

      if (previousRole != null && !currentRole.getChildren().contains(previousRole)) {
        currentRole.getChildren().add(previousRole);
        roleRepository.save(currentRole);
      }

      previousRole = currentRole;
    }

    System.out.println("Roles, permissões e hierarquia inicializadas/atualizadas com sucesso!");
  }

}
