package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.data.Vehicle;
import at.dse.g14.commons.dto.data.VehicleManufacturer;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.VehicleEntity;
import at.dse.g14.entity.VehicleManufacturerEntity;
import at.dse.g14.persistence.VehicleManufacturerRepository;
import at.dse.g14.service.VehicleManufacturerService;
import at.dse.g14.service.VehicleService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
@Service("vehicleManufacturerService")
@Slf4j
public class VehicleManufacturerServiceImpl implements VehicleManufacturerService {

  private final VehicleManufacturerRepository manufacturerRepository;
  private final ModelMapper modelMapper;
  private final Validator validator;

  private final VehicleService vehicleService;

  @Autowired
  public VehicleManufacturerServiceImpl(
      final VehicleManufacturerRepository manufacturerRepository,
      final ModelMapper modelMapper,
      final Validator validator,
      final VehicleService vehicleService) {
    this.manufacturerRepository = manufacturerRepository;
    this.modelMapper = modelMapper;
    this.validator = validator;
    this.vehicleService = vehicleService;
  }

  @Override
  public VehicleManufacturer save(final VehicleManufacturer manufacturer) throws ServiceException {
    validate(manufacturer);
    final VehicleManufacturerEntity entity =
        manufacturerRepository.save(convertToEntity(manufacturer));
    return convertToDto(entity);
  }

  @Override
  public VehicleManufacturer update(final VehicleManufacturer manufacturer)
      throws ServiceException {
    validate(manufacturer);
    if (manufacturer.getId() != null) {
      throw new ValidationException("No ID provided");
    }
    final VehicleManufacturerEntity entity =
        manufacturerRepository.save(convertToEntity(manufacturer));
    return convertToDto(entity);
  }

  @Override
  public void delete(final String manufacturerId) throws ServiceException {
    if (manufacturerId == null) {
      throw new ServiceException("ID is null!");
    }
    manufacturerRepository.deleteById(manufacturerId);
  }

  @Override
  public VehicleManufacturer findOne(final String manufacturerId) throws ServiceException {
    if (manufacturerId == null) {
      throw new ServiceException("ID is null!");
    }
    final Optional<VehicleManufacturerEntity> foundManufacturer =
        manufacturerRepository.findById(manufacturerId);
    if (!foundManufacturer.isPresent()) {
      throw new ServiceException("Unknown manufacturerId " + manufacturerId);
    }
    return convertToDto(foundManufacturer.get());
  }

  @Override
  public VehicleManufacturer findByVin(final String vin) throws ServiceException {
    Vehicle vehicle = vehicleService.findOne(vin);
    return vehicle == null ? null : vehicle.getManufacturer();
  }

  @Override
  public List<VehicleManufacturer> findAll() throws ServiceException {
    return convertToDto((List<VehicleManufacturerEntity>) manufacturerRepository.findAll());
  }

  @Override
  public VehicleManufacturer convertToDto(final VehicleManufacturerEntity entity) {
    return modelMapper.map(entity, VehicleManufacturer.class);
  }

  @Override
  public List<VehicleManufacturer> convertToDto(final List<VehicleManufacturerEntity> entities) {
    return entities.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Override
  public VehicleManufacturerEntity convertToEntity(final VehicleManufacturer dto) {
    final VehicleManufacturerEntity manufacturer =
        modelMapper.map(dto, VehicleManufacturerEntity.class);

    if (dto.getId() != null && !dto.getId().isEmpty()) {

      final List<Vehicle> vehicles = vehicleService.findAllOfManufacturer(dto.getId());
      final List<VehicleEntity> vehicleEntities = vehicleService.convertToEntity(vehicles);

      manufacturer.setVehicles(new HashSet<>(vehicleEntities));
    } else {
      manufacturer.setVehicles(new HashSet<>());
    }

    return manufacturer;
  }

  @Override
  public void validate(final VehicleManufacturer manufacturer) throws ValidationException {
    log.debug("Validating " + manufacturer);
    Set<ConstraintViolation<VehicleManufacturer>> violations = validator.validate(manufacturer);
    if (!violations.isEmpty()) {
      throw new ValidationException(
          "EmergencyService not valid: \n"
              + Arrays.toString(violations.stream().map(Object::toString).toArray()));
    }
  }
}
