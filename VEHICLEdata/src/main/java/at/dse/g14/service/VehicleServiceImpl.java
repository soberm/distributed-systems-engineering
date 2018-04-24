package at.dse.g14.service;

import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.persistence.VehicleRepository;
import at.dse.g14.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Service("vehicleService")
public class VehicleServiceImpl implements VehicleService {

  private final VehicleRepository vehicleRepository;

  @Autowired
  public VehicleServiceImpl(final VehicleRepository vehicleRepository) {
    this.vehicleRepository = vehicleRepository;
  }

  @Override
  public Vehicle create(final Vehicle vehicle) throws ValidationException {
    validate(vehicle);
    return vehicleRepository.save(vehicle);
  }

  @Override
  public Vehicle update(Vehicle vehicle) throws ValidationException {
    validate(vehicle);
    if (vehicle.getId() != null) {
      throw new ValidationException("No ID provided");
    }
    return vehicleRepository.save(vehicle);
  }

  private void validate(final Vehicle vehicle) throws ValidationException {
    if (vehicle.getManufacturer() == null) {
      throw new ValidationException("No manufacturer provided");
    } else if (vehicle.getPosition() == null) {
      throw new ValidationException("No GPS point provided");
    }
  }
}
