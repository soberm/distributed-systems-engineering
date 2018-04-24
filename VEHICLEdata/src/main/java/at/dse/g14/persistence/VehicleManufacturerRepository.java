package at.dse.g14.persistence;

import at.dse.g14.commons.dto.VehicleManufacturer;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
public interface VehicleManufacturerRepository extends CrudRepository<VehicleManufacturer, Long> {}
