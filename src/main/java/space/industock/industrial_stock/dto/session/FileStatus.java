package space.industock.industrial_stock.dto.session;

import java.util.List;
import java.util.UUID;

public class FileStatus {

  public UUID fileId;
  public List<Integer> uploaded;
  public int totalChunks;

}
