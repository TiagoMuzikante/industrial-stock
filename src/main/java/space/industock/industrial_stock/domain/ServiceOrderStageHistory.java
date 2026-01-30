package space.industock.industrial_stock.domain;

import jakarta.persistence.*;
import lombok.*;
import space.industock.industrial_stock.domain.queue.PriorityBlock;
import space.industock.industrial_stock.domain.queue.QueueItem;
import space.industock.industrial_stock.enums.Stage;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
public class ServiceOrderStageHistory extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "service_order_id")
  private ServiceOrder serviceOrder;

  @Column(columnDefinition = "text")
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "service_stage_history_pictures", joinColumns = @JoinColumn(name = "service_order_stage_history_id"))
  private List<String> pictures;

  @Enumerated(EnumType.STRING)
  private Stage oldStage;

  @Enumerated(EnumType.STRING)
  private Stage newStage;

  private Long oldPosition;

  private Long newPosition;

  private Long positionAtExecution;

  private LocalDateTime executedAt;

  @Column(columnDefinition = "text")
  private String notes;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "priority_block_id")
  private PriorityBlock blockAtExecution;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "queue_item_id")
  private QueueItem item;

}


