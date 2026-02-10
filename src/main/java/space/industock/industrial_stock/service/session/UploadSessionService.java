package space.industock.industrial_stock.service.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.industock.industrial_stock.domain.session.UploadChunk;
import space.industock.industrial_stock.domain.session.UploadFile;
import space.industock.industrial_stock.domain.session.UploadSession;
import space.industock.industrial_stock.dto.session.*;
import space.industock.industrial_stock.enums.UploadSessionState;
import space.industock.industrial_stock.repository.session.UploadChunkRepository;
import space.industock.industrial_stock.repository.session.UploadFileRepository;
import space.industock.industrial_stock.repository.session.UploadSessionRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UploadSessionService {

  private final UploadSessionRepository sessionRepository;
  private final UploadFileRepository fileRepository;
  private final UploadChunkRepository chunkRepository;
  private final ObjectMapper mapper = new ObjectMapper();

  @Value("${upload.sessionTtlHours:24}")
  private int ttlHours;

  public UploadSessionService(UploadSessionRepository s, UploadFileRepository f, UploadChunkRepository c) {
    this.sessionRepository = s;
    this.fileRepository = f;
    this.chunkRepository = c;
  }

  @Transactional
  public InitResponse init(InitRequest req) {
    UploadSession session = new UploadSession();
    session.setId(UUID.randomUUID());
    session.setState(UploadSessionState.UPLOADING);
    session.setClientJson(write(req.client));
    session.setServicesJson(write(req.services));
    session.setCreatedAt(Instant.now());
    session.setExpiresAt(Instant.now().plusSeconds(3600L * ttlHours));
    sessionRepository.save(session);

    List<FileIdMapping> mappings = new ArrayList<>();

    for (FileManifestItem fm : req.files) {
      UploadFile uf = new UploadFile();
      uf.setId(UUID.randomUUID());
      uf.setSession(session);
      uf.setLocalId(fm.localId);
      uf.setName(fm.name);
      uf.setSize(fm.size);
      uf.setHash(fm.hash);
      uf.setChunkSize(req.chunkSize);
      uf.setTotalChunks((int) Math.ceil((double) fm.size / req.chunkSize));
      fileRepository.save(uf);

      FileIdMapping m = new FileIdMapping();
      m.localId = fm.localId;
      m.fileId = uf.getId();
      m.totalChunks = uf.getTotalChunks();
      m.chunkSize = uf.getChunkSize();
      mappings.add(m);
    }

    InitResponse res = new InitResponse();
    res.sessionId = session.getId();
    res.files = mappings;
    return res;
  }

  public StatusResponse status(UUID sessionId) {
    UploadSession s = sessionRepository.findById(sessionId).orElseThrow();
    List<UploadFile> files = fileRepository.findBySessionId(sessionId);

    List<FileStatus> stats = new ArrayList<>();
    for (UploadFile f : files) {
      List<UploadChunk> chunks = chunkRepository.findBySessionIdAndFileId(sessionId, f.getId());
      FileStatus fs = new FileStatus();
      fs.fileId = f.getId();
      fs.totalChunks = f.getTotalChunks();
      fs.uploaded = chunks.stream().map(UploadChunk::getChunkIndex).toList();
      stats.add(fs);
    }

    StatusResponse r = new StatusResponse();
    r.sessionId = sessionId;
    r.state = s.getState().name();
    r.files = stats;
    return r;
  }

  private String write(Object o) {
    try {
      return mapper.writeValueAsString(o);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
