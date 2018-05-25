package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.VehicleManufacturer;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.VehicleManufacturerEntity;
import at.dse.g14.persistence.VehicleManufacturerRepository;
import at.dse.g14.service.VehicleManufacturerService;
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
@Service("vehicleManufacturerService")
public class VehicleManufacturerServiceImpl implements VehicleManufacturerService {

  private final VehicleManufacturerRepository manufacturerRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public VehicleManufacturerServiceImpl(
      final VehicleManufacturerRepository manufacturerRepository, final ModelMapper modelMapper) {
    this.manufacturerRepository = manufacturerRepository;
    this.modelMapper = modelMapper;
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
  public void delete(final Long manufacturerId) throws ServiceException {
    if (manufacturerId == null) {
      throw new ServiceException("ID is null!");
    }
    manufacturerRepository.delete(manufacturerId);
  }

  @Override
  public VehicleManufacturer findOne(final Long manufacturerId) throws ServiceException {
    if (manufacturerId == null) {
      throw new ServiceException("ID is null!");
    }
    return convertToDto(manufacturerRepository.findOne(manufacturerId));
  }

  @Override
  public List<VehicleManufacturer> findAll() throws ServiceException {
    return convertToDto((List<VehicleManufacturerEntity>) manufacturerRepository.findAll());
  }

  private VehicleManufacturer convertToDto(final VehicleManufacturerEntity entity) {
    return modelMapper.map(entity, VehicleManufacturer.class);
  }

  private List<VehicleManufacturer> convertToDto(final List<VehicleManufacturerEntity> entities) {
    return entities.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  private VehicleManufacturerEntity convertToEntity(final VehicleManufacturer dto) {
    return modelMapper.map(dto, VehicleManufacturerEntity.class);
  }

  private void validate(final VehicleManufacturer manufacturer) throws ValidationException {
  }
}
