package space.industock.industrial_stock.service;

import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.Client;
import space.industock.industrial_stock.domain.ServiceOrder;
import space.industock.industrial_stock.dto.ClientDTO;
import space.industock.industrial_stock.dto.ClientSimpleDTO;
import space.industock.industrial_stock.enums.Stage;
import space.industock.industrial_stock.event.EnqueueClientServiceEvent;
import space.industock.industrial_stock.repository.ClientRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClientService extends BaseService<Client, ClientDTO> {

  private final ClientRepository repository;
  private final ApplicationEventPublisher publisher;

  public ClientService(ClientRepository repository, ApplicationEventPublisher publisher) {
    super(repository);
    this.repository = repository;
    this.publisher = publisher;
  }

  public List<ClientSimpleDTO> findAllSimple(){
    return repository.findAll().stream().map(this::toSimpleDTO).toList();
  }

  public void finishClient(Long id){
    Client client = super.findById(id);
    client.setFinishedAt(LocalDateTime.now());
    repository.save(client);
  }

  public ClientSimpleDTO findSimpleById(Long id){
    return toSimpleDTO(super.findById(id));
  }

  public ClientSimpleDTO save(ClientSimpleDTO dto){
    Client entity = toEntity(dto);
    return toSimpleDTO(repository.save(entity));
  }

  public ClientSimpleDTO update(ClientSimpleDTO dto, Long id){
    Client entity = toEntity(dto);
    entity.setId(id);
    return toSimpleDTO(repository.save(entity));
  }

  @Override
  public ClientDTO save(ClientDTO clientDTO) {
    Client client = super.toEntity(clientDTO);

    Client saved = repository.save(client);
    publisher.publishEvent(new EnqueueClientServiceEvent(saved, clientDTO.getStage()));
    ClientDTO dto = super.toDto(saved);
    return dto;
  }

  public Client toEntity(ClientSimpleDTO dto) {
    Client client = new Client();
    BeanUtils.copyProperties(dto, client);
    return client;
  }

  public ClientSimpleDTO toSimpleDTO(Client client){
    ClientSimpleDTO dto = new ClientSimpleDTO();
    BeanUtils.copyProperties(client, dto);
    return dto;
  }

}
