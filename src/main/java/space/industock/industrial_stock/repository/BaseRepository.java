package space.industock.industrial_stock.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

  @Query("SELECT e FROM #{#entityName} e WHERE e.active = TRUE")
  @NonNull
  List<T> findAll();

  @Transactional
  @Modifying
  @Query("UPDATE #{#entityName} e SET e.active = FALSE, e.deletedAt = CURRENT_TIMESTAMP WHERE e.id = :id")
  void softDeleteById(ID id);

}
