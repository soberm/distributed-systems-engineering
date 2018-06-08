package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.AccidentEventDTO;
import at.dse.g14.commons.dto.LiveVehicleTrackDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.CrashEventNotification;
import at.dse.g14.persistence.CrashEventNotificationRepository;
import at.dse.g14.service.AbstractCrudService;
import at.dse.g14.service.ICrashEventNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CrashEventNotificationService extends AbstractCrudService<CrashEventNotification, Long>
    implements ICrashEventNotificationService {

  @Autowired
  public CrashEventNotificationService(
      final Validator validator,
      final CrashEventNotificationRepository crashEventNotificationRepository) {
    super(crashEventNotificationRepository, validator);
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
  protected CrashEventNotification updateLoadedEntity(
      CrashEventNotification loadedCrashEventNotification,
      CrashEventNotification crashEventNotification) {
    loadedCrashEventNotification.setReceiver(crashEventNotification.getReceiver());
    loadedCrashEventNotification.setVin(crashEventNotification.getVin());
    loadedCrashEventNotification.setModelType(crashEventNotification.getModelType());
    loadedCrashEventNotification.setPassengers(crashEventNotification.getPassengers());
    loadedCrashEventNotification.setLocation(crashEventNotification.getLocation());
    loadedCrashEventNotification.setSpeed(crashEventNotification.getSpeed());
    loadedCrashEventNotification.setDistanceVehicleAhead(
        crashEventNotification.getDistanceVehicleAhead());
    loadedCrashEventNotification.setDistanceVehicleBehind(
        crashEventNotification.getDistanceVehicleBehind());
    return loadedCrashEventNotification;
  }

  private void validate(AccidentEventDTO accidentEventDTO) throws ValidationException {
    log.debug("Validating " + accidentEventDTO);
    if (!validator.validate(accidentEventDTO).isEmpty()) {
      throw new ValidationException("AccidentEventDTO not valid.");
    }
  }
}
