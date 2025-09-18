package space.industock.industrial_stock.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "app_user")
//@SequenceGenerator (name = "numero_pedido_seq", sequenceName = "numero_pedido_seq", allocationSize = 1 )
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

//  @Column(name = "numero_pedido", insertable = false, updatable = false, columnDefinition = "BIGINT DEFAULT nextval('numero_pedido_seq')")
  private String name;
  private String email;
  private String document_cpf;
  private String password;
  private Integer registerNumber;


  // auth
  private Boolean isEnable;
  private Boolean isAccountNonLocked;
  private Boolean isAccountNonExpired;
  private Boolean isCredentialsNonExpired;
  private String authorities;
}
