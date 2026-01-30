package space.industock.industrial_stock.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import space.industock.industrial_stock.domain.queue.PriorityBlock;
import space.industock.industrial_stock.domain.queue.PriorityBlockItem;
import space.industock.industrial_stock.enums.Stage;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class QueueItemDTO {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Long id;

  private ClientDTO client;

  private boolean isStarted;

  private List<ServiceOrderDTO> serviceOrder;

  private Stage stage;

  private Long originalPosition;

  private PriorityBlock block;

  private PriorityBlockItem priorityItems;

}
