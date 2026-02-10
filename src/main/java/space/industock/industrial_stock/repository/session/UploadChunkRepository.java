package space.industock.industrial_stock.repository.session;

import org.springframework.data.jpa.repository.JpaRepository;
import space.industock.industrial_stock.domain.session.UploadChunk;

import java.util.List;
import java.util.UUID;

public interface UploadChunkRepository extends JpaRepository<UploadChunk, Long> {

  List<UploadChunk> findBySessionIdAndFileId(UUID sessionId, UUID fileId);

  boolean existsBySessionIdAndFileIdAndChunkIndex(UUID sessionId, UUID fileId, int chunkIndex);

  int countBySessionIdAndFileId(UUID sessionId, UUID fileId);
}
