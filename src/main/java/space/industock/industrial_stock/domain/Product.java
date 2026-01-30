package space.industock.industrial_stock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"serviceOrders", "historics"})
public class Product extends BaseEntity{

  private String name;
  private Integer amount;

  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
  private List<ProductHistoric> historics;

  @ManyToMany(mappedBy = "materials", fetch = FetchType.LAZY)
  private List<ServiceOrder> serviceOrders;

}
