package space.industock.industrial_stock.domain;

import jakarta.persistence.*;
import lombok.*;
import space.industock.industrial_stock.enums.ExpenseType;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Expense extends BaseEntity {

  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private ExpenseType type;

  @Column(columnDefinition="text")
  private String description;

  @Column(nullable = false)
  private Integer value;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

}
