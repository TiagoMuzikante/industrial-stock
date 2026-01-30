package space.industock.industrial_stock.repository.queue;

import space.industock.industrial_stock.domain.queue.PriorityBlock;
import space.industock.industrial_stock.domain.queue.PriorityBlockItem;
import space.industock.industrial_stock.repository.BaseRepository;

import java.util.List;

public interface PriorityBlockItemRepository extends BaseRepository<PriorityBlockItem, Long> {

  List<PriorityBlockItem> findByBlockOrderByPosition(PriorityBlock block);

  void deleteByBlockId(Long blockId);

}
