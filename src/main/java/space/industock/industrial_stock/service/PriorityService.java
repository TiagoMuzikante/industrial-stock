package space.industock.industrial_stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.queue.PriorityBlock;
import space.industock.industrial_stock.domain.queue.PriorityBlockItem;
import space.industock.industrial_stock.domain.queue.QueueItem;
import space.industock.industrial_stock.repository.queue.PriorityBlockItemRepository;
import space.industock.industrial_stock.repository.queue.PriorityBlockRepository;
import space.industock.industrial_stock.repository.queue.QueueItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriorityService {

  private final PriorityBlockRepository blockRepo;
  private final PriorityBlockItemRepository blockItemRepo;
  private final QueueItemRepository queueRepo;

  /**
   * Cria um bloco de prioridade para um cliente + stage.
   */
  public PriorityBlock createPriorityBlock(List<Long> queueItemIds) {

    PriorityBlock block = new PriorityBlock();
    block.setActiveBlock(true);
    block = blockRepo.save(block);

    int pos = 1;
    for (Long qiId : queueItemIds) {
      QueueItem qi = queueRepo.findById(qiId)
          .orElseThrow(() -> new RuntimeException("QueueItem não encontrado"));

      PriorityBlockItem item = new PriorityBlockItem();
      item.setItem(qi);
      item.setBlock(block);
      item.setPosition(pos++);

      blockItemRepo.save(item);

      qi.setBlock(block);
      queueRepo.save(qi);
    }

    return block;
  }

  /**
   * Desativa o bloco de prioridade.
   */
  public void deactivateBlock(Long blockId) {
    PriorityBlock pb = blockRepo.findById(blockId)
        .orElseThrow(() -> new RuntimeException("Bloco não encontrado"));

    pb.setActiveBlock(false);
    blockRepo.save(pb);
  }
}
