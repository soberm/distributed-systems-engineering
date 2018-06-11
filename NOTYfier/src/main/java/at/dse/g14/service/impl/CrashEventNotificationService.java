package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.AccidentEventDTO;
import at.dse.g14.commons.dto.EmergencyService;
import at.dse.g14.commons.dto.LiveVehicleTrackDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.CrashEventNotification;
import at.dse.g14.persistence.CrashEventNotificationRepository;
import at.dse.g14.service.AbstractCrudService;
import at.dse.g14.service.ICrashEventNotificationService;
import at.dse.g14.web.client.VehicleDataClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class CrashEventNotificationService extends AbstractCrudService<CrashEventNotification, Long>
    implements ICrashEventNotificationService {

  private final VehicleDataClient vehicleDataClient;

  @Autowired
  public CrashEventNotificationService(
      final Validator validator,
      final CrashEventNotificationRepository crashEventNotificationRepository,
      final VehicleDataClient vehicleDataClient) {
    super(crashEventNotificationRepository, validator);
    this.vehicleDataClient = vehicleDataClient;
  }

  @Override
  public List<CrashEventNotification> generateFrom(AccidentEventDTO accidentEventDTO)
      throws ServiceException {
    validate(accidentEventDTO);
    log.info("Generating CrashEventNotifications for {}", accidentEventDTO);
    List<CrashEventNotification> crashEventNotifications = generateForVehicles(accidentEventDTO);
    crashEventNotifications.add(generateForVehicleManufacturer(accidentEventDTO));
    crashEventNotifications.add(generateForEmergencyService(accidentEventDTO));
    return crashEventNotifications;
  }

  private List<CrashEventNotification> generateForVehicles(AccidentEventDTO accidentEventDTO) {
    log.info("Generating CrashEventNotifications for the vehicles near {}", accidentEventDTO);
    LiveVehicleTrackDTO liveVehicleTrackDTO = accidentEventDTO.getLiveVehicleTrack();
    List<CrashEventNotification> crashEventNotificationsVehicles = new ArrayList<>();
    for (String receiver : accidentEventDTO.getVehiclesInBigRange()) {
      crashEventNotificationsVehicles.add(generateNotification(liveVehicleTrackDTO, receiver));
    }
    return crashEventNotificationsVehicles;
  }

  private CrashEventNotification generateForVehicleManufacturer(AccidentEventDTO accidentEventDTO) {
    log.info(
        "Generating CrashEventNotification for the VehicleManufacturer of {}", accidentEventDTO);
    LiveVehicleTrackDTO liveVehicleTrackDTO = accidentEventDTO.getLiveVehicleTrack();
    String receiver =
        vehicleDataClient.getVehicleManufacturer(liveVehicleTrackDTO.getVin()).getId();
    return generateNotification(liveVehicleTrackDTO, receiver);
  }

  private CrashEventNotification generateForEmergencyService(AccidentEventDTO accidentEventDTO) {
    log.info("Generating CrashEventNotification for a EmergencyService of {}", accidentEventDTO);
    LiveVehicleTrackDTO liveVehicleTrackDTO = accidentEventDTO.getLiveVehicleTrack();
    List<EmergencyService> emergencyServices = vehicleDataClient.getEmergencyServices();
    Random rand = new Random();
    EmergencyService emergencyService =
        emergencyServices.get(rand.nextInt(emergencyServices.size()));
    log.info("{} was chosen to be notified for {}", emergencyService, accidentEventDTO);
    return generateNotification(liveVehicleTrackDTO, emergencyService.getId());
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
