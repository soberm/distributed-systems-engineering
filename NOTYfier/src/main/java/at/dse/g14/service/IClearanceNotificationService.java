package at.dse.g14.service;

import at.dse.g14.commons.dto.events.ClearanceEventDTO;
import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.ClearanceNotification;

import java.util.List;

/**
 * This interface represents the needed functionality around ClearanceNotifications.
 *
 * @author Michael Sober
 * @since 1.0
 * @see CrudService
 */
public interface IClearanceNotificationService extends CrudService<ClearanceNotification, Long> {

  /**
   * Generates ClearanceNotification out of the information contained from the event.
   *
   * @param clearanceEventDTO which contains the event information.
   * @return the generated ClearanceNotifications.
   * @throws ServiceException if an error occurred, while generating the notifications.
   */
  List<ClearanceNotification> generateFrom(ClearanceEventDTO clearanceEventDTO)
      throws ServiceException;
}
