package space.industock.industrial_stock.dto.session;

import java.util.List;
import java.util.UUID;

public class StatusResponse {

  public UUID sessionId;
  public String state;
  public List<FileStatus> files;

}
