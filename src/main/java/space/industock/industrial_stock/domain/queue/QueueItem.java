package space.industock.industrial_stock.domain.queue;

import jakarta.persistence.*;
import lombok.*;
import space.industock.industrial_stock.domain.BaseEntity;
import space.industock.industrial_stock.domain.Client;
import space.industock.industrial_stock.domain.ServiceOrderStageHistory;
import space.industock.industrial_stock.enums.Stage;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"block", "priorityItems", "histories", "client"})
public class QueueItem extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @Enumerated(EnumType.STRING)
  private Stage stage;

  private Long originalPosition;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "block_id")
  private PriorityBlock block;

  @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
  List<PriorityBlockItem> priorityItems;

  @OneToMany(mappedBy = "item")
  List<ServiceOrderStageHistory> histories;

  public QueueItem(Client client, Long originalPosition) {
    this.originalPosition = originalPosition;
    this.client = client;
  }
}
