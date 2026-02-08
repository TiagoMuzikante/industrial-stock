package space.industock.industrial_stock.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import space.industock.industrial_stock.domain.Client;
import space.industock.industrial_stock.domain.ServiceOrder;
import space.industock.industrial_stock.domain.ServicePicture;
import space.industock.industrial_stock.dto.*;
import space.industock.industrial_stock.enums.PictureType;
import space.industock.industrial_stock.repository.ClientRepository;
import space.industock.industrial_stock.repository.ServicePictureRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClientService extends BaseService<Client, ClientDTO> {

  private final ClientRepository repository;
  private final ServicePictureRepository pictureRepository;

  public ClientService(ClientRepository repository, ServicePictureRepository pictureRepository) {
    super(repository);
    this.repository = repository;
    this.pictureRepository = pictureRepository;
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

  public ClientDTO replace(Long id, ClientDTO dto){
    Client client = super.findById(id);
    client.setName(dto.getName());
    client.setAddress(dto.getAddress());
    client.setPhoneNumber(dto.getPhoneNumber());
    client.setPaymentType(dto.getPaymentType());
    client.setPayment(dto.isPayment());
    client.setPaymentTimes(dto.getPaymentTimes());
    client.setPaymentValueInCents(dto.getPaymentValueInCents());

    return toDto(repository.save(client));
  }

  public ClientDTO replaceWithService(Long id, ClientDTO dto){

    return null;

  }


  @Override
  public ClientDTO save(ClientDTO clientDTO) {
    Client client = super.toEntity(clientDTO);

    Client saved = repository.save(client);
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

  public ClientDTO findClientById(Long id){

    Client client = super.findById(id);
    ClientDTO clientDTO = super.toDto(client);
    clientDTO.setServices(client.getServices().stream().map(this::toServiceOrderDTO).toList());

    return clientDTO;
  }

  public ServiceOrderDTO toServiceOrderDTO(ServiceOrder entity) {
    ServiceOrderDTO serviceOrderDTO = super.toDto(entity, ServiceOrderDTO.class);
    serviceOrderDTO.setPictures(pictureRepository.findByServiceOrder_IdAndType(entity.getId(), PictureType.INITIAL).stream().map(ServicePicture::getId).toList());
    serviceOrderDTO.setConfirmProductionPictures(pictureRepository.findByServiceOrder_IdAndType(entity.getId(), PictureType.CONFIRM_PRODUCTION).stream().map(ServicePicture::getId).toList());
    serviceOrderDTO.setConfirmDeliverPictures(pictureRepository.findByServiceOrder_IdAndType(entity.getId(), PictureType.CONFIRM_DELIVER).stream().map(ServicePicture::getId).toList());
    serviceOrderDTO.setCurrentUser(entity.getCurrentUser() != null ? (super.toDto(entity.getCurrentUser(), UserDTO.class)) : null);
    serviceOrderDTO.setProductedByUser(entity.getProductedByUser() != null ? (super.toDto(entity.getProductedByUser(), UserDTO.class)) : null);
    serviceOrderDTO.setMateriais(entity.getMaterials().stream().map(aux -> BaseService.toDto(aux, ProductDTO.class)).toList());
    return serviceOrderDTO;
  }


}
