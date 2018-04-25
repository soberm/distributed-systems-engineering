package at.dse.g14.service;

import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.service.exception.ValidationException;
import java.util.List;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
public interface VehicleService {

  Vehicle create(final Vehicle vehicle) throws ValidationException;

  Vehicle findOne(final long vehicleId, final long manufacturerId) throws ValidationException;

  Vehicle update(final Vehicle vehicle) throws ValidationException;

  List<Vehicle> findAllOfManufacturer(final long manufacturerId);
}
