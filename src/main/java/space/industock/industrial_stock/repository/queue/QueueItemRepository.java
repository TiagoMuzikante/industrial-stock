package space.industock.industrial_stock.repository.queue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import space.industock.industrial_stock.domain.queue.QueueItem;
import space.industock.industrial_stock.enums.Stage;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueueItemRepository extends JpaRepository<QueueItem, Long> {

  @Query("""
        SELECT qi
        FROM QueueItem qi
            LEFT JOIN qi.block pb
            LEFT JOIN PriorityBlockItem pbi ON pbi.item = qi
        WHERE qi.stage = :stage
          AND (pb.activeBlock = TRUE OR pb IS NULL)
        ORDER BY
            CASE WHEN pb IS NULL THEN 0 ELSE 1 END,
            pb.id,
            pbi.position,
            qi.originalPosition
        """)
  List<QueueItem> findNextInStage(@Param("stage") Stage stage);


  @Query("""
        SELECT qi
        FROM QueueItem qi
        LEFT JOIN qi.block pb
        LEFT JOIN PriorityBlockItem pbi ON pbi.item = qi
        WHERE qi.stage = :stage
          AND (pb.activeBlock = TRUE OR pb IS NULL)
        ORDER BY
            CASE WHEN pb IS NULL THEN 0 ELSE 1 END,
            pb.id,
            pbi.position,
            qi.id
        """)
  List<QueueItem> findAllOrdered(
      @Param("stage") Stage stage
  );

  long countByStage(Stage stage);

  Optional<QueueItem> findByClientIdAndStage(Long clientId, Stage stage);
}
