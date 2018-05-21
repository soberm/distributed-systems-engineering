package at.dse.g14.service;

import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.service.exception.ValidationException;
import java.util.List;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
public interface VehicleService extends CrudService<Vehicle, Long> {

  Vehicle findOne(long vehicleId, long manufacturerId)
      throws ValidationException;

  List<Vehicle> findAllOfManufacturer(final long manufacturerId);
}
