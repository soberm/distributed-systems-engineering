package at.dse.g14.service;

import at.dse.g14.commons.dto.data.VehicleManufacturer;
import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.VehicleManufacturerEntity;
import java.util.List;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
public interface VehicleManufacturerService extends CrudService<VehicleManufacturer, String> {

  VehicleManufacturer getByName(final String name);

  VehicleManufacturer findByVin(final String vin) throws ServiceException;

  VehicleManufacturer convertToDto(final VehicleManufacturerEntity entity);

  List<VehicleManufacturer> convertToDto(final List<VehicleManufacturerEntity> entities);

  VehicleManufacturerEntity convertToEntity(final VehicleManufacturer dto);

  void validate(final VehicleManufacturer manufacturer) throws ValidationException;
}
