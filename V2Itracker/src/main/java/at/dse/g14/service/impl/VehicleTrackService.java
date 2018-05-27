package at.dse.g14.service.impl;

import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.LiveVehicleTrack;
import at.dse.g14.entity.VehicleTrack;
import at.dse.g14.persistence.VehicleTrackRepository;
import at.dse.g14.service.ILiveVehicleTrackService;
import at.dse.g14.service.IVehicleTrackService;
import at.dse.g14.service.exception.VehicleTrackAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class VehicleTrackService implements IVehicleTrackService {

  private final Validator validator;
  private final VehicleTrackRepository vehicleTrackRepository;
  private final ILiveVehicleTrackService liveVehicleTrackService;
  private final ModelMapper modelMapper;

  @Autowired
  public VehicleTrackService(
          final Validator validator, final VehicleTrackRepository vehicleTrackRepository,
          final ILiveVehicleTrackService liveVehicleTrackService,
          final ModelMapper modelMapper) {
    this.validator = validator;
    this.vehicleTrackRepository = vehicleTrackRepository;
    this.liveVehicleTrackService = liveVehicleTrackService;
    this.modelMapper = modelMapper;
  }

  @Override
  public VehicleTrack save(VehicleTrack vehicleTrack) throws ServiceException {
    validate(vehicleTrack);

    if (findOne(vehicleTrack.getId()) != null) {
      throw new VehicleTrackAlreadyExistsException(vehicleTrack + " already exists.");
    }

    log.info("Saving " + vehicleTrack);
    VehicleTrack savedVehicleTrack = vehicleTrackRepository.save(vehicleTrack);
    log.info("Saved " + vehicleTrack);
    liveVehicleTrackService.update(convertToLiveVehicleTrack(savedVehicleTrack));

    return savedVehicleTrack;
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
    loadedVehicleTrack = vehicleTrackRepository.save(loadedVehicleTrack);
    liveVehicleTrackService.update(convertToLiveVehicleTrack(loadedVehicleTrack));

    return loadedVehicleTrack;
  }

  @Override
  public void delete(String id) throws ServiceException {
    log.info("Deleted VehicleTrack " + id);
    vehicleTrackRepository.deleteById(id);
  }

  @Override
  public VehicleTrack findOne(String id) throws ServiceException {
    log.info("Finding VehicleTrack " + id);
    try {
      return vehicleTrackRepository.findById(id).get();
    } catch (NoSuchElementException | IllegalArgumentException e) {
      return null;
    }
  }

  @Override
  public List<VehicleTrack> findAll() throws ServiceException {
    log.info("Finding all VehicleTracks.");
    return vehicleTrackRepository.findAll();
  }

  private void validate(VehicleTrack vehicleTrack) throws ValidationException {
    log.debug("Validating " + vehicleTrack);
    if (!validator.validate(vehicleTrack).isEmpty()) {
      throw new ValidationException("VehicleTrack not valid.");
    }
  }

  private LiveVehicleTrack convertToLiveVehicleTrack(VehicleTrack vehicleTrack) {
    return modelMapper.map(vehicleTrack, LiveVehicleTrack.class);
  }

}
