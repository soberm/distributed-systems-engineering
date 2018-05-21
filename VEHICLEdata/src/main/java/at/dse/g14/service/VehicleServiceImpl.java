package at.dse.g14.service;

import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.entity.VehicleEntity;
import at.dse.g14.entity.VehicleManufacturerEntity;
import at.dse.g14.persistence.VehicleManufacturerRepository;
import at.dse.g14.persistence.VehicleRepository;
import at.dse.g14.service.exception.ValidationException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
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
  private final ModelMapper modelMapper;
  private final VehicleManufacturerRepository manufacturerRepository;

  @Autowired
  public VehicleServiceImpl(
      final VehicleRepository vehicleRepository,
      final ModelMapper modelMapper,
      final VehicleManufacturerRepository manufacturerRepository) {
    this.vehicleRepository = vehicleRepository;
    this.modelMapper = modelMapper;
    this.manufacturerRepository = manufacturerRepository;
  }

  @Override
  public Vehicle create(final Vehicle vehicle) throws ValidationException {
    validate(vehicle);
    final VehicleEntity entity = vehicleRepository.save(convertToEntity(vehicle));
    return convertToDto(entity);
  }

  @Override
  public Vehicle findOne(final long vehicleId, final long manufacturerId)
      throws ValidationException {
    final VehicleEntity vehicleEntity = vehicleRepository.findOne(vehicleId);
    final VehicleManufacturerEntity manufacturerEntity =
        manufacturerRepository.findOne(manufacturerId);

    if (!vehicleEntity.getManufacturer().equals(manufacturerEntity)) {
      throw new ValidationException("Vehicle does not belong to provided manufacturer!");
    } else {
      return convertToDto(vehicleEntity);
    }
  }

  @Override
  public Vehicle update(final Vehicle vehicle) throws ValidationException {
    validate(vehicle);
    if (vehicle.getId() != null) {
      throw new ValidationException("No ID provided");
    }
    final VehicleEntity entity = vehicleRepository.save(convertToEntity(vehicle));
    return convertToDto(entity);
  }

  private void validate(final Vehicle vehicle) throws ValidationException {
    if (vehicle.getManufacturer() == null) {
      throw new ValidationException("No manufacturer provided");
    }
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
}