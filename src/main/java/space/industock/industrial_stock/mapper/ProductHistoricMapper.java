package space.industock.industrial_stock.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import space.industock.industrial_stock.domain.Product;
import space.industock.industrial_stock.domain.User;
import space.industock.industrial_stock.dto.productHistory.ProductHistoryDTO;
import space.industock.industrial_stock.dto.productHistory.HistoricInnerDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class ProductHistoricMapper {

  private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public List<ProductHistoryDTO> toHistoricByUserDTO(List<User> users, LocalDate startDate, LocalDate endDate){
    return users.stream()
        .map(user -> ProductHistoryDTO.builder()
            .id(user.getId().toString())
            .name(user.getName())
            .historics(user.getProductHistoricsBetweenDates(startDate, endDate)
                .stream().map(productHistoric -> new HistoricInnerDTO(
                    productHistoric.getProduct().getName(),
                    productHistoric.getIsIncoming(),
                    productHistoric.getAmount(),
                    productHistoric.getOldAmount(),
                    productHistoric.getChangedAmount(),
                    LocalDate.parse(dateTimeFormatter.format(productHistoric.getDateTime())))).toList()).build())
        .filter(historic -> !historic.getHistorics().isEmpty())
        .toList();
  }

  public List<ProductHistoryDTO> toHistoricByProductDTO(List<Product> products, LocalDate startDate, LocalDate endDate){
    return products.stream()
        .map(product -> ProductHistoryDTO.builder()
            .id(product.getId().toString())
            .name(product.getName())
            .historics(product.getHistoricsBetweenDates(startDate, endDate)
                .stream().map(productHistoric -> new HistoricInnerDTO(
                    productHistoric.getUser().getName(),
                    productHistoric.getIsIncoming(),
                    productHistoric.getAmount(),
                    productHistoric.getOldAmount(),
                    productHistoric.getChangedAmount(),
                    LocalDate.parse(dateTimeFormatter.format(productHistoric.getDateTime())))).toList()).build())
        .filter(historic -> !historic.getHistorics().isEmpty())
        .toList();
  }
}
