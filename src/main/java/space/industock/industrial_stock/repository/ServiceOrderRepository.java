package space.industock.industrial_stock.repository;

import space.industock.industrial_stock.domain.Client;
import space.industock.industrial_stock.domain.ServiceOrder;
import space.industock.industrial_stock.enums.Stage;

import java.util.List;

public interface ServiceOrderRepository extends BaseRepository<ServiceOrder, Long> {

  List<ServiceOrder> findByStage(Stage stage);

  List<ServiceOrder> findByFinished(boolean finished);

  List<ServiceOrder> findByClientIdAndStage(Long clientId, Stage stage);

  List<ServiceOrder> findByClientAndStage(Client client, Stage stage);

  boolean existsByClientIdAndStage(Long client_id, Stage stage);

  List<ServiceOrder> findServiceOrdersByClientIdAndStage(Long client_id, Stage stage);

}
