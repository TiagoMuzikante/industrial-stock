package space.industock.industrial_stock.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import space.industock.industrial_stock.domain.session.UploadChunk;
import space.industock.industrial_stock.dto.session.CommitResponse;
import space.industock.industrial_stock.dto.session.InitRequest;
import space.industock.industrial_stock.dto.session.InitResponse;
import space.industock.industrial_stock.dto.session.StatusResponse;
import space.industock.industrial_stock.enums.PictureType;
import space.industock.industrial_stock.repository.session.UploadChunkRepository;
import space.industock.industrial_stock.service.ImageStorageService;
import space.industock.industrial_stock.service.session.UploadSessionService;
import space.industock.industrial_stock.service.session.commit.ServiceOrderCommit;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/upload-sessions")
@CrossOrigin
public class UploadSessionController {

  private final UploadSessionService sessionService;
  private final ImageStorageService storage;
  private final UploadChunkRepository chunkRepository;
  private final ServiceOrderCommit serviceOrderCommit;

  public UploadSessionController(UploadSessionService sessionService, ImageStorageService storage, UploadChunkRepository chunkRepository, ServiceOrderCommit serviceOrderCommit) {
    this.sessionService = sessionService;
    this.storage = storage;
    this.chunkRepository = chunkRepository;
    this.serviceOrderCommit = serviceOrderCommit;
  }

  @PostMapping("/init")
  public InitResponse init(@RequestBody InitRequest req) {
    return sessionService.init(req);
  }

  @PutMapping("/{sessionId}/files/{fileId}/chunks")
  public Object putChunk(@PathVariable UUID sessionId, @PathVariable UUID fileId, @RequestHeader("X-Chunk-Index") int index, HttpServletRequest request) throws IOException {
    InputStream body = request.getInputStream();

    long size = request.getContentLengthLong();
    String contentType = request.getContentType();

    if (chunkRepository.existsBySessionIdAndFileIdAndChunkIndex(
        sessionId, fileId, index)) {
      return Map.of("stored", true, "duplicate", true);
    }

    storage.writeChunk(sessionId, fileId, index, body, size, contentType);

    UploadChunk c = new UploadChunk();
    c.setSessionId(sessionId);
    c.setFileId(fileId);
    c.setChunkIndex(index);
    c.setCreatedAt(Instant.now());
    chunkRepository.save(c);

    return Map.of("stored", true);
  }

  @GetMapping("/{sessionId}/status")
  public StatusResponse status(@PathVariable UUID sessionId) {
    return sessionService.status(sessionId);
  }

  @PostMapping("/commit/service/{sessionId}/{type}")
  public CommitResponse commit(@PathVariable UUID sessionId, @PathVariable PictureType type) {
    return serviceOrderCommit.commitService(sessionId, type);
  }

}
