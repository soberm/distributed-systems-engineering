package at.dse.g14.service.impl;

import at.dse.g14.FeignExampleController;
import at.dse.g14.commons.dto.data.EmergencyService;
import at.dse.g14.commons.dto.events.AccidentEventDTO;
import at.dse.g14.commons.dto.track.LiveVehicleTrackDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.CrashEventNotification;
import at.dse.g14.persistence.CrashEventNotificationRepository;
import at.dse.g14.service.AbstractCrudService;
import at.dse.g14.service.ICrashEventNotificationService;
import at.dse.g14.web.client.VehicleDataClient;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CrashEventNotificationService extends AbstractCrudService<CrashEventNotification, Long>
    implements ICrashEventNotificationService {

  private final VehicleDataClient vehicleDataClient;
  private static Logger log = LoggerFactory.getLogger(FeignExampleController.class);

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
    crashEventNotifications.addAll(generateForEmergencyService(accidentEventDTO));
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
    log.error("RECEIVER Manufacturer: " + receiver);
    return generateNotification(liveVehicleTrackDTO, receiver);
  }

  private List<CrashEventNotification> generateForEmergencyService(
      AccidentEventDTO accidentEventDTO) {
    log.info("Generating CrashEventNotification for a EmergencyService of {}", accidentEventDTO);
    LiveVehicleTrackDTO liveVehicleTrackDTO = accidentEventDTO.getLiveVehicleTrack();
    List<CrashEventNotification> crashEventNotificationsEmergencyServices = new ArrayList<>();
    List<EmergencyService> emergencyServices = vehicleDataClient.getEmergencyServices();
    for (EmergencyService emergencyService : emergencyServices) {
      crashEventNotificationsEmergencyServices.add(
          generateNotification(liveVehicleTrackDTO, emergencyService.getId()));
    }
    return crashEventNotificationsEmergencyServices;
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
