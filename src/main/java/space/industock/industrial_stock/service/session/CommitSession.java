package space.industock.industrial_stock.service.session;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.industock.industrial_stock.domain.session.UploadFile;
import space.industock.industrial_stock.domain.session.UploadSession;
import space.industock.industrial_stock.domain.utils.FileUploadResult;
import space.industock.industrial_stock.enums.UploadSessionState;
import space.industock.industrial_stock.infra.DetectContentType;
import space.industock.industrial_stock.repository.session.UploadChunkRepository;
import space.industock.industrial_stock.repository.session.UploadFileRepository;
import space.industock.industrial_stock.repository.session.UploadSessionRepository;
import space.industock.industrial_stock.service.ImageStorageService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CommitSession {

  private final UploadSessionRepository sessionRepository;
  private final UploadChunkRepository chunkRepository;
  private final ImageStorageService storage;
  private final UploadFileRepository uploadFileRepository;

  public CommitSession(UploadSessionRepository sessionRepository, UploadChunkRepository chunkRepository, ImageStorageService storage, UploadFileRepository uploadFileRepository) {
    this.sessionRepository = sessionRepository;
    this.chunkRepository = chunkRepository;
    this.storage = storage;
    this.uploadFileRepository = uploadFileRepository;
  }

  @Transactional
  public List<FileUploadResult> commitSession(UUID sessionId) {
    UploadSession session = sessionRepository.findById(sessionId)
        .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));

    List<UploadFile> files = uploadFileRepository.findBySessionId(sessionId);

    // 1️⃣ validar chunks
    for (UploadFile f : files) {
      int uploadedChunks = chunkRepository.countBySessionIdAndFileId(sessionId, f.getId());
      if (uploadedChunks != f.getTotalChunks()) {
        throw new IllegalStateException("Chunks incompletos para " + f.getName());
      }
    }

    // 2️⃣ compor arquivos no storage
    List<FileUploadResult> results = new ArrayList<>();
    for (UploadFile f : files) {
      String objectName = storage.compose(sessionId, f.getId(), f.getTotalChunks(), f.getName());
      String contentType = DetectContentType.detectByName(f.getName());

      results.add(new FileUploadResult(
          f.getId(),
          f.getLocalId(),
          objectName,
          contentType,
          f.getName(),
          f.getSize()
      ));

    }

    session.setState(UploadSessionState.COMMITTED);
    sessionRepository.save(session);

    return results;
  }
}
