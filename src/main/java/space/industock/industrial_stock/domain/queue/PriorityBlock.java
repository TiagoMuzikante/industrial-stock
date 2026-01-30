package space.industock.industrial_stock.domain.queue;

import jakarta.persistence.*;
import lombok.*;
import space.industock.industrial_stock.domain.BaseEntity;
import space.industock.industrial_stock.domain.ServiceOrderStageHistory;
import space.industock.industrial_stock.enums.Stage;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"items"})
public class PriorityBlock extends BaseEntity {

  private String name;

  private boolean activeBlock = true;

//  @OneToMany(mappedBy = "blockAtExecution")
//  private List<ServiceOrderStageHistory> histories;

  @OneToMany(mappedBy = "block", cascade = CascadeType.ALL)
  private List<PriorityBlockItem> items = new ArrayList<>();

}
