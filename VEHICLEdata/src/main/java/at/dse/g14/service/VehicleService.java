package at.dse.g14.service;

import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.service.exception.ValidationException;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
public interface VehicleService {

  Vehicle create(final Vehicle vehicle) throws ValidationException;

  Vehicle update(Vehicle vehicle) throws ValidationException;
}
