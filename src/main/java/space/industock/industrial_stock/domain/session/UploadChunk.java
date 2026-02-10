package space.industock.industrial_stock.domain.session;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(
    columnNames = {"sessionId","fileId","chunkIndex"}
))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadChunk {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private UUID sessionId;
  private UUID fileId;
  private int chunkIndex;
  private long size;
  private Instant createdAt;

}
