package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.AccidentEventDTO;
import at.dse.g14.commons.dto.LiveVehicleTrackDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.CrashEventNotification;
import at.dse.g14.persistence.CrashEventNotificationRepository;
import at.dse.g14.service.ICrashEventNotificationService;
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
public class CrashEventNotificationService implements ICrashEventNotificationService {

  private final Validator validator;
  private final CrashEventNotificationRepository crashEventNotificationRepository;

  @Autowired
  public CrashEventNotificationService(
      final Validator validator,
      final CrashEventNotificationRepository crashEventNotificationRepository) {
    this.validator = validator;
    this.crashEventNotificationRepository = crashEventNotificationRepository;
  }

  @Override
  public List<CrashEventNotification> generateFrom(AccidentEventDTO accidentEventDTO)
      throws ServiceException {
    validate(accidentEventDTO);
    log.info("Generating CrashEventNotifications for {}", accidentEventDTO);
    // TODO: Generate for other receivers
    return generateForVehicles(accidentEventDTO);
  }

  private List<CrashEventNotification> generateForVehicles(AccidentEventDTO accidentEventDTO) {
    log.info("Generating CrashEventNotifications for the vehicles near {}", accidentEventDTO);
    LiveVehicleTrackDTO liveVehicleTrackDTO = accidentEventDTO.getLiveVehicleTrack();
    List<CrashEventNotification> crashEventNotificationsVehicles = new ArrayList<>();
    for (String receiver : accidentEventDTO.getVehiclesInRange()) {
      crashEventNotificationsVehicles.add(generateNotification(liveVehicleTrackDTO, receiver));
    }
    return crashEventNotificationsVehicles;
  }

  private CrashEventNotification generateNotification(
      LiveVehicleTrackDTO liveVehicleTrackDTO, String receiver) {
    return CrashEventNotification.builder()
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
  public CrashEventNotification save(CrashEventNotification crashEventNotification)
      throws ServiceException {
    validate(crashEventNotification);

    if (findOne(crashEventNotification.getId()) != null) {
      throw new NotificationAlreadyExistsException(crashEventNotification + " already exists.");
    }

    log.info("Saving " + crashEventNotification);
    return crashEventNotificationRepository.save(crashEventNotification);
  }

  @Override
  public CrashEventNotification update(CrashEventNotification crashEventNotification)
      throws ServiceException {
    validate(crashEventNotification);

    CrashEventNotification loadedCrashEventNotification = findOne(crashEventNotification.getId());

    if (loadedCrashEventNotification == null) {
      log.info(crashEventNotification + " does not exist. Creating a new one.");
      return save(crashEventNotification);
    }

    loadedCrashEventNotification.setVin(crashEventNotification.getVin());
    loadedCrashEventNotification.setModelType(crashEventNotification.getModelType());
    loadedCrashEventNotification.setPassengers(crashEventNotification.getPassengers());
    loadedCrashEventNotification.setLocation(crashEventNotification.getLocation());
    loadedCrashEventNotification.setSpeed(crashEventNotification.getSpeed());
    loadedCrashEventNotification.setDistanceVehicleAhead(
        crashEventNotification.getDistanceVehicleAhead());
    loadedCrashEventNotification.setDistanceVehicleBehind(
        crashEventNotification.getDistanceVehicleBehind());

    log.info("Updating " + loadedCrashEventNotification);
    return crashEventNotificationRepository.save(loadedCrashEventNotification);
  }

  @Override
  public void delete(Long id) throws ServiceException {
    log.info("Deleted CrashEventNotification " + id);
    crashEventNotificationRepository.deleteById(id);
  }

  @Override
  public CrashEventNotification findOne(Long id) throws ServiceException {
    log.info("Finding CrashEventNotification " + id);
    if (id == null) {
      return null;
    }

    try {
      return crashEventNotificationRepository.findById(id).get();
    } catch (NoSuchElementException | IllegalArgumentException e) {
      return null;
    }
  }

  @Override
  public List<CrashEventNotification> findAll() throws ServiceException {
    log.info("Finding all CrashEventNotification.");
    return (List<CrashEventNotification>) crashEventNotificationRepository.findAll();
  }

  private void validate(AccidentEventDTO accidentEventDTO) throws ValidationException {
    log.debug("Validating " + accidentEventDTO);
    if (!validator.validate(accidentEventDTO).isEmpty()) {
      throw new ValidationException("AccidentEventDTO not valid.");
    }
  }

  private void validate(CrashEventNotification crashEventNotification) throws ValidationException {
    log.debug("Validating " + crashEventNotification);
    if (!validator.validate(crashEventNotification).isEmpty()) {
      throw new ValidationException("CrashEventNotification not valid.");
    }
  }
}
