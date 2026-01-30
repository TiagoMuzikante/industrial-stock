package space.industock.industrial_stock.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"product", "user"})
public class ProductHistoric extends BaseEntity{

  private Integer amount;
  private Integer oldAmount;
  private Integer changedAmount;
  private Boolean isIncoming;

  @ManyToOne
  private Product product;

  @ManyToOne
  private User user;

}
