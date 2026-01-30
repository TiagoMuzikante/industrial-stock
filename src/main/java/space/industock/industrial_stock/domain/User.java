package space.industock.industrial_stock.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "app_user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String name;
  private String password;
  private boolean restartPassword = true;

  @ToString.Exclude
  @OneToMany(mappedBy = "currentUser")
  private List<ServiceOrder> currentServices;

  // auth
  private boolean isEnable = true;
  private boolean isAccountNonLocked = true;
  private boolean isAccountNonExpired = true;
  private boolean isCredentialsNonExpired = true;

  @OneToMany(mappedBy = "user")
  @ToString.Exclude
  @JsonIgnore
  private List<ProductHistoric> productHistorics;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "role_id")
  private Role role;

  //registro de etapas

//  @ToString.Exclude
//  @JsonIgnore
//  @OneToMany(mappedBy = "productorUser")
//  List<ServiceOrder> productedServices;

  public String getAccessLevel(){
    return role.getName();
  }

}
