package space.industock.industrial_stock.repository.queue;

import org.springframework.data.jpa.repository.JpaRepository;
import space.industock.industrial_stock.domain.queue.PriorityBlock;

public interface PriorityBlockRepository extends JpaRepository<PriorityBlock, Long> {
}
