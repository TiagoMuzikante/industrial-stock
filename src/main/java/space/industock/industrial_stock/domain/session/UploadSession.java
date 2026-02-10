package space.industock.industrial_stock.domain.session;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import space.industock.industrial_stock.enums.UploadSessionState;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadSession{

  @Id
  private UUID id;

  @Enumerated(EnumType.STRING)
  private UploadSessionState state;

  @Lob
  private String clientJson;

  @Lob
  private String ServicesJson;

  private Instant createdAt;
  private Instant expiresAt;

}
