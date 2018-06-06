package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.VehicleEntity;
import at.dse.g14.entity.VehicleManufacturerEntity;
import at.dse.g14.persistence.VehicleManufacturerRepository;
import at.dse.g14.persistence.VehicleRepository;
import at.dse.g14.service.VehicleService;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Service("vehicleService")
@Slf4j
public class VehicleServiceImpl implements VehicleService {

  private final VehicleRepository vehicleRepository;
  private final ModelMapper modelMapper;
  private final VehicleManufacturerRepository manufacturerRepository;
  private final Validator validator;

  @Autowired
  public VehicleServiceImpl(
      final VehicleRepository vehicleRepository,
      final ModelMapper modelMapper,
      final VehicleManufacturerRepository manufacturerRepository,
      final Validator validator) {
    this.vehicleRepository = vehicleRepository;
    this.modelMapper = modelMapper;
    this.manufacturerRepository = manufacturerRepository;
    this.validator = validator;
  }

  @Override
  public Vehicle save(final Vehicle vehicle) throws ValidationException {
    validate(vehicle);
    final VehicleEntity entity = vehicleRepository.save(convertToEntity(vehicle));
    return convertToDto(entity);
  }

  @Override
  public Vehicle findOne(final long vehicleId, final long manufacturerId)
      throws ValidationException {
    final VehicleEntity vehicleEntity = vehicleRepository.findById(vehicleId).get();
    final VehicleManufacturerEntity manufacturerEntity =
        manufacturerRepository.findById(manufacturerId).get();

    if (!vehicleEntity.getManufacturer().equals(manufacturerEntity)) {
      throw new ValidationException("Vehicle does not belong to provided manufacturer!");
    } else {
      return convertToDto(vehicleEntity);
    }
  }

  @Override
  public Vehicle update(final Vehicle vehicle) throws ServiceException {
    validate(vehicle);
    if (vehicle.getId() != null) {
      throw new ValidationException("No ID provided");
    }
    final VehicleEntity entity = vehicleRepository.save(convertToEntity(vehicle));
    return convertToDto(entity);
  }

  @Override
  public void delete(final Long vehicleId) throws ServiceException {
    if (vehicleId == null) {
      throw new ServiceException("ID is null!");
    }
    vehicleRepository.deleteById(vehicleId);
  }

  @Override
  public Vehicle findOne(final Long vehicleId) throws ServiceException {
    if (vehicleId == null) {
      throw new ServiceException("ID is null!");
    }
    return convertToDto(vehicleRepository.findById(vehicleId).get());
  }

  @Override
  public List<Vehicle> findAll() throws ServiceException {
    return convertToDto((List<VehicleEntity>) vehicleRepository.findAll());
  }

  @Override
  public List<Vehicle> findAllOfManufacturer(long manufacturerId) {
    return convertToDto(vehicleRepository.findAllByManufacturer_Id(manufacturerId));
  }

  private Vehicle convertToDto(VehicleEntity entity) {
    return modelMapper.map(entity, Vehicle.class);
  }

  private List<Vehicle> convertToDto(final List<VehicleEntity> entities) {
    return entities.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  private VehicleEntity convertToEntity(Vehicle dto) {
    return modelMapper.map(dto, VehicleEntity.class);
  }

  private List<VehicleEntity> convertToEntity(final List<Vehicle> entities) {
    return entities.stream().map(this::convertToEntity).collect(Collectors.toList());
  }

  private void validate(final Vehicle vehicle) throws ValidationException {
    log.debug("Validating " + vehicle);
    Set<ConstraintViolation<Vehicle>> violations = validator.validate(vehicle);
    if (!violations.isEmpty()) {
      throw new ValidationException("Vehicle not valid: \n" +
          Arrays.toString(
              violations.stream()
                  .map(Object::toString)
                  .toArray())
      );
    }
  }
}
