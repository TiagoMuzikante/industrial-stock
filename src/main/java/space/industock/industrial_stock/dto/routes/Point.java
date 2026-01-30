package space.industock.industrial_stock.dto.routes;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Point {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private int job;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Integer id;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String type;

  private List<Double> location;

}
