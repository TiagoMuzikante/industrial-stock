package space.industock.industrial_stock.domain.queue;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import space.industock.industrial_stock.domain.BaseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"block", "item"})
public class PriorityBlockItem extends BaseEntity {

  private int position;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "block_id")
  private PriorityBlock block;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "queue_item_id")
  private QueueItem item;

}
