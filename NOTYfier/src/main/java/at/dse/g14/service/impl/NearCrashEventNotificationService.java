package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.AccidentEventDTO;
import at.dse.g14.commons.dto.LiveVehicleTrackDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.NearCrashEventNotification;
import at.dse.g14.persistence.NearCrashEventNotificationRepository;
import at.dse.g14.service.AbstractCrudService;
import at.dse.g14.service.INearCrashEventNotificationService;
import at.dse.g14.web.client.VehicleDataClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NearCrashEventNotificationService
    extends AbstractCrudService<NearCrashEventNotification, Long>
    implements INearCrashEventNotificationService {

  private final VehicleDataClient vehicleDataClient;

  @Autowired
  public NearCrashEventNotificationService(
      final Validator validator,
      final NearCrashEventNotificationRepository nearCrashEventNotificationRepository,
      final VehicleDataClient vehicleDataClient) {
    super(nearCrashEventNotificationRepository, validator);
    this.vehicleDataClient = vehicleDataClient;
  }

  @Override
  public List<NearCrashEventNotification> generateFrom(AccidentEventDTO accidentEventDTO)
      throws ServiceException {
    validate(accidentEventDTO);
    log.info("Generating NearCrashEventNotifications for {}", accidentEventDTO);
    List<NearCrashEventNotification> nearCrashEventNotifications = new ArrayList<>();
    nearCrashEventNotifications.add(generateForVehicleManufacturer(accidentEventDTO));
    return nearCrashEventNotifications;
  }

  /*private List<NearCrashEventNotification> generateForVehicles(AccidentEventDTO accidentEventDTO) {
    log.info("Generating NearCrashEventNotification for the vehicles near {}", accidentEventDTO);
    LiveVehicleTrackDTO liveVehicleTrackDTO = accidentEventDTO.getLiveVehicleTrack();
    List<NearCrashEventNotification> nearCrashEventNotificationsVehicles = new ArrayList<>();
    for (String receiver : accidentEventDTO.getVehiclesInBigRange()) {
      nearCrashEventNotificationsVehicles.add(generateNotification(liveVehicleTrackDTO, receiver));
    }
    return nearCrashEventNotificationsVehicles;
  }*/

  private NearCrashEventNotification generateForVehicleManufacturer(
      AccidentEventDTO accidentEventDTO) {
    log.info(
        "Generating CrashEventNotification for the VehicleManufacturer of {}", accidentEventDTO);
    LiveVehicleTrackDTO liveVehicleTrackDTO = accidentEventDTO.getLiveVehicleTrack();
    String receiver =
        vehicleDataClient.getVehicleManufacturer(liveVehicleTrackDTO.getVin()).getId();
    return generateNotification(liveVehicleTrackDTO, receiver);
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
  protected NearCrashEventNotification updateLoadedEntity(
      NearCrashEventNotification loadedNearCrashEventNotification,
      NearCrashEventNotification nearCrashEventNotification) {
    loadedNearCrashEventNotification.setReceiver(nearCrashEventNotification.getReceiver());
    loadedNearCrashEventNotification.setVin(nearCrashEventNotification.getVin());
    loadedNearCrashEventNotification.setModelType(nearCrashEventNotification.getModelType());
    loadedNearCrashEventNotification.setPassengers(nearCrashEventNotification.getPassengers());
    loadedNearCrashEventNotification.setLocation(nearCrashEventNotification.getLocation());
    loadedNearCrashEventNotification.setSpeed(nearCrashEventNotification.getSpeed());
    loadedNearCrashEventNotification.setDistanceVehicleAhead(
        nearCrashEventNotification.getDistanceVehicleAhead());
    loadedNearCrashEventNotification.setDistanceVehicleBehind(
        nearCrashEventNotification.getDistanceVehicleBehind());
    return loadedNearCrashEventNotification;
  }

  private void validate(AccidentEventDTO accidentEventDTO) throws ValidationException {
    log.debug("Validating " + accidentEventDTO);
    if (!validator.validate(accidentEventDTO).isEmpty()) {
      throw new ValidationException("AccidentEventDTO not valid.");
    }
  }
}
