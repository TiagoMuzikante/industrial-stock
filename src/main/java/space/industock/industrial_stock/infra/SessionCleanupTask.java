package space.industock.industrial_stock.infra;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import space.industock.industrial_stock.enums.UploadSessionState;
import space.industock.industrial_stock.repository.session.UploadSessionRepository;

import java.time.Instant;

@Component
public class SessionCleanupTask {

  private final UploadSessionRepository repository;

  public SessionCleanupTask(UploadSessionRepository repository) {
    this.repository = repository;
  }

  @Scheduled(fixedDelay = 3600000)
  public void run() {
    repository.findAll().forEach(s -> {
      if (s.getState() != UploadSessionState.COMMITTED &&
          s.getExpiresAt() != null &&
          s.getExpiresAt().isBefore(Instant.now())) {
        s.setState(UploadSessionState.EXPIRED);
        repository.save(s);
      }
    });
  }

}
