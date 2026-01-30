package space.industock.industrial_stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import space.industock.industrial_stock.domain.ProductHistoric;
import space.industock.industrial_stock.dto.ProductDTO;
import space.industock.industrial_stock.dto.UserDTO;
import space.industock.industrial_stock.dto.productHistory.HistoricInnerDTO;
import space.industock.industrial_stock.dto.productHistory.ProductHistoryDTO;
import space.industock.industrial_stock.event.ProductMovimentEvent;
import space.industock.industrial_stock.exception.UnauthorizedException;
import space.industock.industrial_stock.repository.ProductHistoricRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductHistoricService extends BaseService<ProductHistoric, ProductHistoryDTO> {

  private final ProductHistoricRepository repository;
  private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public ProductHistoricService(ProductHistoricRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void saveProductHistoric(ProductMovimentEvent productMovimentEvent){
    ProductHistoric productHistoric = new ProductHistoric(
        productMovimentEvent.amount(),
        productMovimentEvent.oldAmount(),
        productMovimentEvent.changedAmount(),
        productMovimentEvent.isIncoming(),
        productMovimentEvent.product(),
        productMovimentEvent.user());

    repository.save(productHistoric);
  }

  public  List<ProductHistoryDTO> findHistoricBetweenDates(String order, LocalDate startDate, LocalDate endDate){

    if(order.equals("product")){
      return repository.findAllBetweenDates(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX)).stream()
          .collect(Collectors.groupingBy(ProductHistoric::getProduct))
          .entrySet().stream().collect(Collectors.toMap(
              en -> toDto(en.getKey(), ProductDTO.class),
              en -> en.getValue().stream().map(
                  ph -> toHistoricInnerDTO(ph, true))
                  .toList()
          )).entrySet().stream().map(
              entry -> new ProductHistoryDTO(
                  entry.getKey().getId(),
                  entry.getKey().getName(),
                  entry.getValue()
              )).toList();
    }else if (order.equals("user")){
      return repository.findAllBetweenDates(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX)).stream()
          .collect(Collectors.groupingBy(ProductHistoric::getUser))
          .entrySet().stream().collect(Collectors.toMap(
              en -> toDto(en.getKey(), UserDTO.class),
              en -> en.getValue().stream().map(
                      ph -> toHistoricInnerDTO(ph, false))
                  .toList()
          )).entrySet().stream().map(
              entry -> new ProductHistoryDTO(
                  entry.getKey().getId(),
                  entry.getKey().getName(),
                  entry.getValue()
              )).toList();
    }else {
      throw new UnauthorizedException("OrderBy is not accepted");
    }
  }

  public HistoricInnerDTO toHistoricInnerDTO(ProductHistoric ph, boolean useUser){
    return HistoricInnerDTO.builder()
        .name(useUser ? ph.getUser().getName() : ph.getProduct().getName())
        .isIncoming(ph.getIsIncoming())
        .amount(ph.getAmount())
        .oldAmount(ph.getOldAmount())
        .changedAmount(ph.getChangedAmount())
        .date(LocalDate.parse(dateTimeFormatter.format(ph.getCreatedAt()))).build();
  }

}
