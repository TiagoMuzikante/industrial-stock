package space.industock.industrial_stock.domain.session;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadFile {

  @Id
  private UUID id;

  @ManyToOne(optional = false)
  private UploadSession session;

  private String localId;
  private String name;
  private long size;
  private String hash;

  private int chunkSize;
  private int totalChunks;

}
