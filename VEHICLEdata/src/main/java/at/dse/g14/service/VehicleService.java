package at.dse.g14.service;

import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.VehicleEntity;
import java.util.List;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
public interface VehicleService extends CrudService<Vehicle, String> {

  Vehicle findOne(String vehicleId, String manufacturerId) throws ServiceException;

  List<Vehicle> findAllOfManufacturer(String manufacturerId);

  Vehicle convertToDto(VehicleEntity entity);

  List<Vehicle> convertToDto(List<VehicleEntity> entities);

  VehicleEntity convertToEntity(Vehicle dto);

  List<VehicleEntity> convertToEntity(List<Vehicle> entities);

  void validate(Vehicle vehicle) throws ValidationException;
}
