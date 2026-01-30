package space.industock.industrial_stock.domain;

import jakarta.persistence.*;
import lombok.*;
import space.industock.industrial_stock.domain.queue.QueueItem;
import space.industock.industrial_stock.domain.utils.AddressDetails;
import space.industock.industrial_stock.enums.PaymentType;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
public class Client extends BaseEntity{

  @Column(nullable = false)
  private String name;

  private LocalDateTime finishedAt;

  @Column(nullable = false)
  private String phoneNumber;

  @Enumerated(EnumType.ORDINAL)
  private PaymentType paymentType;

  private Integer paymentValueInCents;
  private Integer paymentTimes;

  private boolean payment;

  @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
  private List<QueueItem> queueItems;

  @Embedded
  private AddressDetails address;

  @OneToMany(mappedBy = "client")
  private List<ServiceOrder> services;

}
