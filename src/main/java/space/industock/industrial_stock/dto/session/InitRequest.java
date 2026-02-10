package space.industock.industrial_stock.dto.session;

import java.util.List;
import java.util.Map;

public class InitRequest {

  public Map<String, Object> client;
  public List<Map<String, Object>> services;
  public List<FileManifestItem> files;
  public int chunkSize;

}
