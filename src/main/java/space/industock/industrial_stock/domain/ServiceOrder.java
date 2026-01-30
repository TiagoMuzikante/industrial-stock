package space.industock.industrial_stock.domain;

import jakarta.persistence.*;
import lombok.*;
import space.industock.industrial_stock.dto.routes.LngLat;
import space.industock.industrial_stock.enums.Stage;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ServiceOrder extends BaseEntity{

  private String model;
  private Integer valueInCents = 0;
  private boolean needWeld = false;
  private boolean needPaint = false;
  private boolean finished = false;
  private Integer multiplier = 1;

  @Column(columnDefinition = "text")
  private String observation;

  @Enumerated(EnumType.STRING)
  private Stage stage;

  @ManyToMany
  @JoinTable(name = "service_order_product",
      joinColumns = @JoinColumn(name = "service_order_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Product> materials;


  @OneToMany(mappedBy = "serviceOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<ServicePicture> pictures = new ArrayList<>();

  @Embedded
  private LngLat deliveredLocation;

  @OneToMany(mappedBy = "serviceOrder")
  private List<ServiceOrderStageHistory> history;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client client;

  @ManyToOne
  @JoinColumn(name = "current_user_id")
  private User currentUser;

  //registro de etapas

//  @ManyToOne
//  @JoinColumn(name = "productor_user_id")
//  private User productorUser;

}
