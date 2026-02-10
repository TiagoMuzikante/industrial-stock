package space.industock.industrial_stock.repository.session;

import org.springframework.data.jpa.repository.JpaRepository;
import space.industock.industrial_stock.domain.session.UploadFile;

import java.util.List;
import java.util.UUID;

public interface UploadFileRepository extends JpaRepository<UploadFile, UUID> {

  List<UploadFile> findBySessionId(UUID sessionId);

}
