package space.industock.industrial_stock.repository.session;

import org.springframework.data.jpa.repository.JpaRepository;
import space.industock.industrial_stock.domain.session.UploadSession;

import java.util.UUID;

public interface UploadSessionRepository extends JpaRepository<UploadSession, UUID> {

}
