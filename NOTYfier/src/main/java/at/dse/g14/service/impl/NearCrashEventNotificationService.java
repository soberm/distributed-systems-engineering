package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.AccidentEventDTO;
import at.dse.g14.commons.dto.LiveVehicleTrackDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.NearCrashEventNotification;
import at.dse.g14.persistence.NearCrashEventNotificationRepository;
import at.dse.g14.service.INearCrashEventNotificationService;
import at.dse.g14.service.exception.NotificationAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class NearCrashEventNotificationService implements INearCrashEventNotificationService {

  private final Validator validator;
  private final NearCrashEventNotificationRepository nearCrashEventNotificationRepository;

  @Autowired
  public NearCrashEventNotificationService(
      final Validator validator,
      final NearCrashEventNotificationRepository nearCrashEventNotificationRepository) {
    this.validator = validator;
    this.nearCrashEventNotificationRepository = nearCrashEventNotificationRepository;
  }

  @Override
  public List<NearCrashEventNotification> generateFrom(AccidentEventDTO accidentEventDTO)
      throws ServiceException {
    validate(accidentEventDTO);
    log.info("Generating NearCrashEventNotifications for {}", accidentEventDTO);
    // TODO: Generate for other receivers
    return generateForVehicles(accidentEventDTO);
  }

  private List<NearCrashEventNotification> generateForVehicles(AccidentEventDTO accidentEventDTO) {
    log.info("Generating NearCrashEventNotification for the vehicles near {}", accidentEventDTO);
    LiveVehicleTrackDTO liveVehicleTrackDTO = accidentEventDTO.getLiveVehicleTrack();
    List<NearCrashEventNotification> nearCrashEventNotificationsVehicles = new ArrayList<>();
    for (String receiver : accidentEventDTO.getVehiclesInRange()) {
      nearCrashEventNotificationsVehicles.add(generateNotification(liveVehicleTrackDTO, receiver));
    }
    return nearCrashEventNotificationsVehicles;
  }

  private NearCrashEventNotification generateNotification(
      LiveVehicleTrackDTO liveVehicleTrackDTO, String receiver) {
    return NearCrashEventNotification.builder()
        .receiver(receiver)
        .vin(liveVehicleTrackDTO.getVin())
        .modelType(liveVehicleTrackDTO.getModelType())
        .passengers(liveVehicleTrackDTO.getPassengers())
        .location(liveVehicleTrackDTO.getLocation())
        .speed(liveVehicleTrackDTO.getSpeed())
        .distanceVehicleAhead(liveVehicleTrackDTO.getDistanceVehicleAhead())
        .distanceVehicleBehind(liveVehicleTrackDTO.getDistanceVehicleBehind())
        .build();
  }

  @Override
  public NearCrashEventNotification save(NearCrashEventNotification nearCrashEventNotification)
      throws ServiceException {
    validate(nearCrashEventNotification);

    if (findOne(nearCrashEventNotification.getId()) != null) {
      throw new NotificationAlreadyExistsException(nearCrashEventNotification + " already exists.");
    }

    log.info("Saving " + nearCrashEventNotification);
    return nearCrashEventNotificationRepository.save(nearCrashEventNotification);
  }

  @Override
  public NearCrashEventNotification update(NearCrashEventNotification nearCrashEventNotification)
      throws ServiceException {
    validate(nearCrashEventNotification);

    NearCrashEventNotification loadedNearCrashEventNotification =
        findOne(nearCrashEventNotification.getId());

    if (loadedNearCrashEventNotification == null) {
      log.info(nearCrashEventNotification + " does not exist. Creating a new one.");
      return save(nearCrashEventNotification);
    }

    loadedNearCrashEventNotification.setVin(nearCrashEventNotification.getVin());
    loadedNearCrashEventNotification.setModelType(nearCrashEventNotification.getModelType());
    loadedNearCrashEventNotification.setPassengers(nearCrashEventNotification.getPassengers());
    loadedNearCrashEventNotification.setLocation(nearCrashEventNotification.getLocation());
    loadedNearCrashEventNotification.setSpeed(nearCrashEventNotification.getSpeed());
    loadedNearCrashEventNotification.setDistanceVehicleAhead(
        nearCrashEventNotification.getDistanceVehicleAhead());
    loadedNearCrashEventNotification.setDistanceVehicleBehind(
        nearCrashEventNotification.getDistanceVehicleBehind());

    log.info("Updating " + loadedNearCrashEventNotification);
    return nearCrashEventNotificationRepository.save(loadedNearCrashEventNotification);
  }

  @Override
  public void delete(Long id) throws ServiceException {
    log.info("Deleted NearCrashEventNotification " + id);
    nearCrashEventNotificationRepository.deleteById(id);
  }

  @Override
  public NearCrashEventNotification findOne(Long id) throws ServiceException {
    log.info("Finding NearCrashEventNotification " + id);
    if (id == null) {
      return null;
    }

    try {
      return nearCrashEventNotificationRepository.findById(id).get();
    } catch (NoSuchElementException | IllegalArgumentException e) {
      return null;
    }
  }

  @Override
  public List<NearCrashEventNotification> findAll() throws ServiceException {
    log.info("Finding all NearCrashEventNotification.");
    return (List<NearCrashEventNotification>) nearCrashEventNotificationRepository.findAll();
  }

  private void validate(AccidentEventDTO accidentEventDTO) throws ValidationException {
    log.debug("Validating " + accidentEventDTO);
    if (!validator.validate(accidentEventDTO).isEmpty()) {
      throw new ValidationException("AccidentEventDTO not valid.");
    }
  }

  private void validate(NearCrashEventNotification nearCrashEventNotification)
      throws ValidationException {
    log.debug("Validating " + nearCrashEventNotification);
    if (!validator.validate(nearCrashEventNotification).isEmpty()) {
      throw new ValidationException("NearCrashEventNotification not valid.");
    }
  }
}
