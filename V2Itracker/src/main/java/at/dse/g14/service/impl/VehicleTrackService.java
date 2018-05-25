package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.VehicleTrack;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.VehicleTrackEntity;
import at.dse.g14.persistence.VehicleTrackRepository;
import at.dse.g14.service.IVehicleTrackService;
import at.dse.g14.service.exception.VehicleTrackAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VehicleTrackService implements IVehicleTrackService {

  private final Validator validator;
  private final VehicleTrackRepository vehicleTrackRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public VehicleTrackService(
      final Validator validator,
      final VehicleTrackRepository vehicleTrackRepository,
      final ModelMapper modelMapper) {
    this.validator = validator;
    this.vehicleTrackRepository = vehicleTrackRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public VehicleTrack save(VehicleTrack vehicleTrack) throws ServiceException {
    validate(vehicleTrack);

    if (findOne(vehicleTrack.getId()) != null) {
      throw new VehicleTrackAlreadyExistsException(vehicleTrack + " already exists.");
    }

    log.info("Saving " + vehicleTrack);
    return convertToDto(vehicleTrackRepository.save(convertToEntity(vehicleTrack)));
  }

  @Override
  public VehicleTrack update(VehicleTrack vehicleTrack) throws ServiceException {
    validate(vehicleTrack);

    VehicleTrack loadedVehicleTrack = findOne(vehicleTrack.getId());

    if (loadedVehicleTrack == null) {
      log.info(vehicleTrack + " does not exist. Creating a new one.");
      return save(vehicleTrack);
    }

    loadedVehicleTrack.setVin(vehicleTrack.getVin());
    loadedVehicleTrack.setModelType(vehicleTrack.getModelType());
    loadedVehicleTrack.setPassengers(vehicleTrack.getPassengers());
    loadedVehicleTrack.setLocation(vehicleTrack.getLocation());
    loadedVehicleTrack.setSpeed(vehicleTrack.getSpeed());
    loadedVehicleTrack.setDistanceVehicleAhead(vehicleTrack.getDistanceVehicleAhead());
    loadedVehicleTrack.setDistanceVehicleBehind(vehicleTrack.getDistanceVehicleBehind());
    loadedVehicleTrack.setNearCrashEvent(vehicleTrack.getNearCrashEvent());
    loadedVehicleTrack.setCrashEvent(vehicleTrack.getCrashEvent());

    log.info("Updating " + loadedVehicleTrack);
    return convertToDto(vehicleTrackRepository.save(convertToEntity(loadedVehicleTrack)));
  }

  @Override
  public void delete(Long id) throws ServiceException {
    validate(id);
    log.info("Deleted VehicleTrack " + id);
    vehicleTrackRepository.deleteById(id);
  }

  @Override
  public VehicleTrack findOne(Long id) throws ServiceException {
    validate(id);
    log.info("Finding VehicleTrack " + id);
    try {
      return convertToDto(vehicleTrackRepository.findById(id).get());
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  @Override
  public List<VehicleTrack> findAll() throws ServiceException {
    log.info("Finding all VehicleTracks.");
    return convertToDto(vehicleTrackRepository.findAll());
  }

  private void validate(VehicleTrack vehicleTrack) throws ValidationException {
    log.debug("Validating " + vehicleTrack);
    if (!validator.validate(vehicleTrack).isEmpty()) {
      throw new ValidationException("VehicleTrack not valid.");
    }
  }

  private void validate(Long id) throws ValidationException {
    log.debug("Validating Id for VehicleTracks " + id);
    if (id < 0) {
      throw new ValidationException("Id must be greater than 0.");
    }
  }

  private VehicleTrack convertToDto(VehicleTrackEntity entity) {
    return modelMapper.map(entity, VehicleTrack.class);
  }

  private List<VehicleTrack> convertToDto(final List<VehicleTrackEntity> entities) {
    return entities.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  private VehicleTrackEntity convertToEntity(VehicleTrack dto) {
    return modelMapper.map(dto, VehicleTrackEntity.class);
  }

  private List<VehicleTrackEntity> convertToEntity(final List<VehicleTrack> entities) {
    return entities.stream().map(this::convertToEntity).collect(Collectors.toList());
  }
}
