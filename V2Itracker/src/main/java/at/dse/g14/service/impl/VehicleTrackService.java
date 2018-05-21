package at.dse.g14.service.impl;

import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.VehicleTrackEntity;
import at.dse.g14.persistence.VehicleTrackRepository;
import at.dse.g14.service.IVehicleTrackService;
import at.dse.g14.service.exception.VehicleTrackAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VehicleTrackService implements IVehicleTrackService {

  @Autowired
  private Validator validator;

  @Autowired
  private VehicleTrackRepository vehicleTrackRepository;

  @Override
  public VehicleTrackEntity save(VehicleTrackEntity vehicleTrack) throws ServiceException {
    validate(vehicleTrack);

    if (findOne(vehicleTrack.getId()) != null) {
      throw new VehicleTrackAlreadyExistsException(vehicleTrack + " already exists.");
    }

    log.info("Saving " + vehicleTrack);
    return vehicleTrackRepository.save(vehicleTrack);
  }

  @Override
  public VehicleTrackEntity update(VehicleTrackEntity vehicleTrack) throws ServiceException {
    validate(vehicleTrack);

    VehicleTrackEntity loadedVehicleTrack = findOne(vehicleTrack.getId());

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
    return vehicleTrackRepository.save(loadedVehicleTrack);
  }

  @Override
  public void delete(Long id) throws ServiceException {
    validate(id);
    log.info("Deleted VehicleTrack " + id);
    vehicleTrackRepository.deleteById(id);
  }

  @Override
  public VehicleTrackEntity findOne(Long id) throws ServiceException {
    validate(id);
    log.info("Finding VehicleTrack " + id);
    try{
      return vehicleTrackRepository.findById(id).get();
    }catch(NoSuchElementException e){
      return null;
    }
  }

  @Override
  public List<VehicleTrackEntity> findAll() throws ServiceException {
    log.info("Finding all VehicleTracks.");
    return vehicleTrackRepository.findAll();
  }

  private void validate(VehicleTrackEntity vehicleTrack) throws ValidationException {
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

}
