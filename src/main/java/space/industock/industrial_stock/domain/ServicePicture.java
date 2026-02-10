package space.industock.industrial_stock.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import space.industock.industrial_stock.enums.PictureType;

@Entity
@Table(name = "service_pictures")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ServicePicture {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  private String checksum;

  @Column(nullable = false, unique = true)
  private String objectName;

  @Column(nullable = false)
  private String contentType;

  @Enumerated(EnumType.STRING)
  private PictureType type;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "service_order_id", nullable = false)
  private ServiceOrder serviceOrder;

}
