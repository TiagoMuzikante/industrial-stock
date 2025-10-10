package space.industock.industrial_stock.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "app_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String name;
  private String password;
  private Boolean restartPassword = false;

  // auth
  private Boolean isEnable;
  private Boolean isAccountNonLocked;
  private Boolean isAccountNonExpired;
  private Boolean isCredentialsNonExpired;
  private String authorities;

  @OneToMany(mappedBy = "user")
  private List<ProductHistoric> productHistorics;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "role_id")
  private Role role;

  public List<ProductHistoric> getProductHistoricsBetweenDates(LocalDate startDate, LocalDate endDate){
    return productHistorics.stream()
        .filter(historic -> (!historic.getDateTime().toLocalDate().isBefore(startDate) && !historic.getDateTime().toLocalDate().isAfter(endDate)))
        .collect(Collectors.toList());
  }
}
