package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.AccidentEventDTO;
import at.dse.g14.commons.dto.LiveVehicleTrackDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.config.PubSubConfig.AccidentEventOutboundGateway;
import at.dse.g14.entity.LiveVehicleTrack;
import at.dse.g14.persistence.LiveVehicleTrackRepository;
import at.dse.g14.service.ILiveVehicleTrackService;
import at.dse.g14.service.exception.LiveVehicleTrackAlreadyExistsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LiveVehicleTrackService implements ILiveVehicleTrackService {

  private static final int BIG_RANGE = 10;
  private static final int SMALL_RANGE = 1;

  private final Validator validator;
  private final LiveVehicleTrackRepository liveVehicleTrackRepository;
  private final ModelMapper modelMapper;
  private final ObjectMapper objectMapper;
  private final AccidentEventOutboundGateway accidentEventOutboundGateway;

  @Autowired
  public LiveVehicleTrackService(
      final Validator validator,
      final LiveVehicleTrackRepository liveVehicleTrackRepository,
      final ModelMapper modelMapper,
      final AccidentEventOutboundGateway accidentEventOutboundGateway,
      final ObjectMapper objectMapper) {
    this.validator = validator;
    this.liveVehicleTrackRepository = liveVehicleTrackRepository;
    this.modelMapper = modelMapper;
    this.accidentEventOutboundGateway = accidentEventOutboundGateway;
    this.objectMapper = objectMapper;
  }

  @Override
  public LiveVehicleTrack save(LiveVehicleTrack liveVehicleTrack) throws ServiceException {
    validate(liveVehicleTrack);

    if (findOne(liveVehicleTrack.getVin()) != null) {
      throw new LiveVehicleTrackAlreadyExistsException(liveVehicleTrack + " already exists.");
    }

    log.info("Saving " + liveVehicleTrack);
    LiveVehicleTrack savedLiveVehicleTrack = liveVehicleTrackRepository.save(liveVehicleTrack);
    log.info("Saved " + liveVehicleTrack);

    if(savedLiveVehicleTrack.getCrashEvent() || savedLiveVehicleTrack.getNearCrashEvent()){
      handleAccidentEvent(savedLiveVehicleTrack);
    }

    return savedLiveVehicleTrack;
  }

  private void handleAccidentEvent(LiveVehicleTrack liveVehicleTrack) {
    Double[] location = liveVehicleTrack.getLocation();
    Point accidentPoint = new Point(location[0], location[1]);
    Distance bigRangeToOtherVehicles = new Distance(BIG_RANGE, Metrics.KILOMETERS);
    Distance smallRangeToOtherVehicles = new Distance(SMALL_RANGE, Metrics.KILOMETERS);

    List<LiveVehicleTrack> vehiclesInBigRange = findByLocationNear(accidentPoint, bigRangeToOtherVehicles);
    List<LiveVehicleTrack> vehiclesInSmallRange = findByLocationNear(accidentPoint, smallRangeToOtherVehicles);
    List<String> vehicleBigRangeVINs = vehiclesInBigRange
            .stream()
            .map(LiveVehicleTrack::getVin)
            .collect(Collectors.toList());
    List<String> vehicleSmallRangeVINs = vehiclesInSmallRange
            .stream()
            .map(LiveVehicleTrack::getVin)
            .collect(Collectors.toList());

    AccidentEventDTO accidentEventDTO = new AccidentEventDTO(
            convertToLiveVehicleTrackDTO(liveVehicleTrack),
            vehicleBigRangeVINs, vehicleSmallRangeVINs);
    try {
      accidentEventOutboundGateway.sendToPubsub(objectMapper.writeValueAsString(accidentEventDTO));
    } catch (JsonProcessingException e) {
      log.error("Could not send AccidentEvent to PubSub.", e);
    }
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

    log.info("Updating " + liveVehicleTrack);
    loadedLiveVehicleTrack = liveVehicleTrackRepository.save(loadedLiveVehicleTrack);

    if(loadedLiveVehicleTrack.getCrashEvent() || loadedLiveVehicleTrack.getNearCrashEvent()){
      handleAccidentEvent(loadedLiveVehicleTrack);
    }

    return loadedLiveVehicleTrack;
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
    } catch (NoSuchElementException | IllegalArgumentException e) {
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

  private LiveVehicleTrackDTO convertToLiveVehicleTrackDTO(LiveVehicleTrack liveVehicleTrack) {
    return modelMapper.map(liveVehicleTrack, LiveVehicleTrackDTO.class);
  }

}
