package space.industock.industrial_stock.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private Integer amount;

  @OneToMany(mappedBy = "product")
  private List<ProductHistoric> historics;


  public List<ProductHistoric> getHistoricsBetweenDates(LocalDate startDate, LocalDate endDate){
    return historics.stream()
        .filter(historic -> (!historic.getDateTime().toLocalDate().isBefore(startDate) && !historic.getDateTime().toLocalDate().isAfter(endDate)))
        .collect(Collectors.toList());
  }

}
