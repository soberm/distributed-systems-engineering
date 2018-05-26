package at.dse.g14.service.impl;

import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.LiveVehicleTrack;
import at.dse.g14.persistence.LiveVehicleTrackRepository;
import at.dse.g14.service.ILiveVehicleTrackService;
import at.dse.g14.service.exception.LiveVehicleTrackAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class LiveVehicleTrackService implements ILiveVehicleTrackService {

  private final Validator validator;
  private final LiveVehicleTrackRepository liveVehicleTrackRepository;

  @Autowired
  public LiveVehicleTrackService(
      final Validator validator, final LiveVehicleTrackRepository liveVehicleTrackRepository) {
    this.validator = validator;
    this.liveVehicleTrackRepository = liveVehicleTrackRepository;
  }

  @Override
  public LiveVehicleTrack save(LiveVehicleTrack liveVehicleTrack) throws ServiceException {
    validate(liveVehicleTrack);

    if (findOne(liveVehicleTrack.getVin()) != null) {
      throw new LiveVehicleTrackAlreadyExistsException(liveVehicleTrack + " already exists.");
    }

    //TODO: Create CrashInfo or NearCrashInfo and send it to NOTYfier

    log.info("Saving " + liveVehicleTrack);
    return liveVehicleTrackRepository.save(liveVehicleTrack);
  }

  @Override
  public LiveVehicleTrack update(LiveVehicleTrack liveVehicleTrack) throws ServiceException {
    validate(liveVehicleTrack);

    LiveVehicleTrack loadedLiveVehicleTrack = findOne(liveVehicleTrack.getVin());

    if (loadedLiveVehicleTrack == null) {
      log.info(liveVehicleTrack + " does not exist. Creating a new one.");
      return save(liveVehicleTrack);
    }

    loadedLiveVehicleTrack.setModelType(liveVehicleTrack.getModelType());
    loadedLiveVehicleTrack.setPassengers(liveVehicleTrack.getPassengers());
    loadedLiveVehicleTrack.setLocation(liveVehicleTrack.getLocation());
    loadedLiveVehicleTrack.setSpeed(liveVehicleTrack.getSpeed());
    loadedLiveVehicleTrack.setDistanceVehicleAhead(liveVehicleTrack.getDistanceVehicleAhead());
    loadedLiveVehicleTrack.setDistanceVehicleBehind(liveVehicleTrack.getDistanceVehicleBehind());
    loadedLiveVehicleTrack.setNearCrashEvent(liveVehicleTrack.getNearCrashEvent());
    loadedLiveVehicleTrack.setCrashEvent(liveVehicleTrack.getCrashEvent());

    //TODO: Create CrashInfo or NearCrashInfo and send it to NOTYfier

    log.info("Updating " + liveVehicleTrack);
    return liveVehicleTrackRepository.save(liveVehicleTrack);
  }

  @Override
  public void delete(String vin) throws ServiceException {
    log.info("Deleted LiveVehicleTrack " + vin);
    liveVehicleTrackRepository.deleteById(vin);
  }

  @Override
  public LiveVehicleTrack findOne(String vin) throws ServiceException {
    log.info("Finding LiveVehicleTrack " + vin);
    try {
      return liveVehicleTrackRepository.findById(vin).get();
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  @Override
  public List<LiveVehicleTrack> findAll() throws ServiceException {
    log.info("Finding all LiveVehicleTrack.");
    return liveVehicleTrackRepository.findAll();
  }

  @Override
  public List<LiveVehicleTrack> findByLocationNear(Point p, Distance d) {
    log.info("Finding LiveVehicleTrack near {}", p);
    return liveVehicleTrackRepository.findByLocationNear(p, d);
  }

  private void validate(LiveVehicleTrack liveVehicleTrack) throws ValidationException {
    log.debug("Validating " + liveVehicleTrack);
    if (!validator.validate(liveVehicleTrack).isEmpty()) {
      throw new ValidationException("LiveVehicleTrack not valid.");
    }
  }
}
