package space.industock.industrial_stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import space.industock.industrial_stock.domain.ServicePicture;
import space.industock.industrial_stock.enums.PictureType;

import java.util.List;

public interface ServicePictureRepository extends JpaRepository<ServicePicture, Long> {

  List<ServicePicture> findByServiceOrder_IdAndType(Long serviceOrderId, PictureType type);


}
