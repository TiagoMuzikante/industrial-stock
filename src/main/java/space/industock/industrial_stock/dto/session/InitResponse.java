package space.industock.industrial_stock.dto.session;

import java.util.List;
import java.util.UUID;

public class InitResponse {

  public UUID sessionId;
  public List<FileIdMapping> files;
}
