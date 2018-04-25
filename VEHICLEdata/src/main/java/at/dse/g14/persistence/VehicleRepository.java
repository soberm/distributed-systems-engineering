package at.dse.g14.persistence;

import at.dse.g14.entity.VehicleEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Repository
public interface VehicleRepository extends CrudRepository<VehicleEntity, Long> {

  List<VehicleEntity> findAllByManufacturer_Id(long id);
}
