package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.data.Vehicle;
import at.dse.g14.commons.dto.events.ClearanceEventDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.ClearanceNotification;
import at.dse.g14.persistence.ClearanceNotificationRepository;
import at.dse.g14.service.AbstractCrudService;
import at.dse.g14.service.IClearanceNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the functionality around ClearanceNotifications.
 *
 * @author Michael Sober
 * @since 1.0
 * @see AbstractCrudService
 * @see IClearanceNotificationService
 */
@Slf4j
@Service
public class ClearanceNotificationService extends AbstractCrudService<ClearanceNotification, Long>
    implements IClearanceNotificationService {

  @Autowired
  public ClearanceNotificationService(
      final Validator validator,
      final ClearanceNotificationRepository clearanceNotificationRepository) {
    super(clearanceNotificationRepository, validator);
  }

  @Override
  protected ClearanceNotification updateLoadedEntity(
      ClearanceNotification loadedClearanceNotification,
      ClearanceNotification clearanceNotification) {
    loadedClearanceNotification.setReceiver(clearanceNotification.getReceiver());
    return loadedClearanceNotification;
  }

  private void validate(ClearanceEventDTO clearanceEventDTO) throws ValidationException {
    log.debug("Validating " + clearanceEventDTO);
    if (!validator.validate(clearanceEventDTO).isEmpty()) {
      throw new ValidationException("ClearanceEventDTO not valid.");
    }
  }

  @Override
  public List<ClearanceNotification> generateFrom(ClearanceEventDTO clearanceEventDTO)
      throws ServiceException {
    validate(clearanceEventDTO);
    log.info("Generating ClearanceNotifications for {}", clearanceEventDTO);
    return generateForVehicles(clearanceEventDTO);
  }

  private List<ClearanceNotification> generateForVehicles(ClearanceEventDTO clearanceEventDTO) {
    log.info("Generating ClearanceNotification for the vehicles near {}", clearanceEventDTO);
    List<ClearanceNotification> clearanceNotificationsVehicles = new ArrayList<>();
    for (Vehicle vehicle : clearanceEventDTO.getVehiclesToNotify()) {
      clearanceNotificationsVehicles.add(new ClearanceNotification(null, vehicle.getVin()));
    }
    return clearanceNotificationsVehicles;
  }
}
