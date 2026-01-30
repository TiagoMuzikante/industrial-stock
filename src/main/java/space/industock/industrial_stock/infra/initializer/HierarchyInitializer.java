package space.industock.industrial_stock.infra.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import space.industock.industrial_stock.domain.Permission;
import space.industock.industrial_stock.domain.Role;
import space.industock.industrial_stock.enums.RoleEnum;
import space.industock.industrial_stock.enums.permissions.Deliver;
import space.industock.industrial_stock.enums.permissions.Manager;
import space.industock.industrial_stock.enums.permissions.Owner;
import space.industock.industrial_stock.enums.permissions.Worker;
import space.industock.industrial_stock.repository.PermissionRepository;
import space.industock.industrial_stock.repository.RoleRepository;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HierarchyInitializer implements CommandLineRunner {

  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;

  @Override
  @Transactional
  public void run(String... args) throws Exception {

    // Map que associa cada RoleEnum a um enum de permissões
    Map<RoleEnum, Class<? extends Enum<?>>> roleReferences = Map.of(
      RoleEnum.WORKER, Worker.class,
      RoleEnum.DELIVER, Deliver.class,
      RoleEnum.MANAGER, Manager.class,
      RoleEnum.OWNER, Owner.class
    );

    Map<RoleEnum, Role> roleMap = new LinkedHashMap<>();

    // Criar/atualizar roles
    for (RoleEnum re : RoleEnum.values()) {
      String roleName = "ROLE_" + re.name();
      Role role = roleRepository.findByName(roleName).orElse(new Role(roleName));

      // Obter enum de permissões correspondente
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

    // Limpa hierarquia antiga em memória
    roleMap.values().forEach(role -> role.getChildren().clear());

    Role owner   = roleMap.get(RoleEnum.OWNER);
    Role manager = roleMap.get(RoleEnum.MANAGER);
    Role worker  = roleMap.get(RoleEnum.WORKER);
    Role deliver = roleMap.get(RoleEnum.DELIVER);

// OWNER > MANAGER > WORKER
    owner.getChildren().add(manager);
    manager.getChildren().add(worker);

// OWNER > DELIVER (DELIVER isolado)
    owner.getChildren().add(deliver);

    roleRepository.save(owner);
    roleRepository.save(manager);


    //System.out.println("Roles, permissões e hierarquia inicializadas/atualizadas com sucesso!");
  }

}
