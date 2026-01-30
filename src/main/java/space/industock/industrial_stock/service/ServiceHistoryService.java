package space.industock.industrial_stock.service;

import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.ServiceOrder;
import space.industock.industrial_stock.domain.ServiceOrderStageHistory;
import space.industock.industrial_stock.domain.utils.ImageBody;
import space.industock.industrial_stock.dto.ServiceOrderDTO;
import space.industock.industrial_stock.exception.InternalAuthorizedException;
import space.industock.industrial_stock.repository.ServiceOrderStageHistoryRepository;

import java.util.List;

@Service
public class ServiceHistoryService {

  private ServiceOrderStageHistoryRepository repository;

  public ServiceHistoryService(ServiceOrderStageHistoryRepository repository) {
    this.repository = repository;
  }

  public ImageBody findImageById(Long id, int index){
    ServiceOrderStageHistory serviceHistrory = repository.findById(id).orElseThrow(() -> new InternalAuthorizedException("Não encontrado."));
    return new ImageBody(serviceHistrory.getPictures().get(index));
  }

  public void addImage(Long id, ImageBody body){
    ServiceOrderStageHistory serviceHistrory = repository.findById(id).orElseThrow(() -> new InternalAuthorizedException("Não encontrado."));
    serviceHistrory.getPictures().add(body.getImage());
    repository.save(serviceHistrory);
  }
}
