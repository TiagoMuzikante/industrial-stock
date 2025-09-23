package space.industock.industrial_stock.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductHistoric {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer amount;
  private Integer oldAmount;
  private Integer changedAmount;
  private LocalDateTime dateTime;
  private Boolean isIncoming;

  @ManyToOne
  private Product product;

  @ManyToOne
  private User user;

}
