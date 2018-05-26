package at.dse.g14.service.impl;

import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.VehicleTrack;
import at.dse.g14.persistence.VehicleTrackRepository;
import at.dse.g14.service.IVehicleTrackService;
import at.dse.g14.service.exception.VehicleTrackAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class VehicleTrackService implements IVehicleTrackService {

  private static final int RANGE = 10;

  private final Validator validator;
  private final VehicleTrackRepository vehicleTrackRepository;

  @Autowired
  public VehicleTrackService(
      final Validator validator, final VehicleTrackRepository vehicleTrackRepository) {
    this.validator = validator;
    this.vehicleTrackRepository = vehicleTrackRepository;
  }

  @Override
  public VehicleTrack save(VehicleTrack vehicleTrack) throws ServiceException {
    validate(vehicleTrack);

    if (findOne(vehicleTrack.getId()) != null) {
      throw new VehicleTrackAlreadyExistsException(vehicleTrack + " already exists.");
    }

    VehicleTrack savedVehicleTrack = vehicleTrackRepository.save(vehicleTrack);
    log.info("Saving " + vehicleTrack);


    if(savedVehicleTrack.getCrashEvent() || savedVehicleTrack.getNearCrashEvent()){
      Double[] location = savedVehicleTrack.getLocation();

      Point accidentLocation = new Point(location[0], location[1]);
      Distance notifyDistance = new Distance(RANGE, Metrics.KILOMETERS);
      List<VehicleTrack> vehicleTracksNear = vehicleTrackRepository.findByLocationNear(accidentLocation, notifyDistance);

      //TODO: Filter for the latest VehicleTrack of each Vehicle
    }

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
    return vehicleTrackRepository.save(loadedVehicleTrack);
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
      return vehicleTrackRepository.findById(id).get();
    } catch (NoSuchElementException e) {
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

  private void validate(Long id) throws ValidationException {
    log.debug("Validating Id for VehicleTracks " + id);
    if (id < 0) {
      throw new ValidationException("Id must be greater than 0.");
    }
  }

  @Override
  public List<VehicleTrack> findByLocationNear(Point p, Distance d) {
    log.info("Finding VehicleTracks near {}" + p);
    return vehicleTrackRepository.findByLocationNear(p, d);
  }

}
