package space.industock.industrial_stock.domain.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Getter
@Setter
public class FileUploadResult {

  private UUID fileId;
  private String localId;
  private String objectName;
  private String contentType;
  private String name;
  private long size;

  public FileUploadResult(UUID fileId, String localId, String objectName, String contentType, String name, long size) {
    this.fileId = fileId;
    this.localId = localId;
    this.objectName = objectName;
    this.contentType = contentType;
    this.name = name;
    this.size = size;
  }
}
