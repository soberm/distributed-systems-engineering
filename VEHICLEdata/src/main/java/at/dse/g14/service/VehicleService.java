package at.dse.g14.service;

import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import java.util.List;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
public interface VehicleService extends CrudService<Vehicle, String> {

  Vehicle findOne(String vehicleId, String manufacturerId)
      throws ServiceException;

  List<Vehicle> findAllOfManufacturer(final String manufacturerId);
}
