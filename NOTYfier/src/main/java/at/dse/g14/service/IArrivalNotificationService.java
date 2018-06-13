package at.dse.g14.service;

import at.dse.g14.commons.dto.events.ArrivalEventDTO;
import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.ArrivalNotification;

import java.util.List;

/**
 * This interface represents the needed functionality around ArrivalNotifications.
 *
 * @author Michael Sober
 * @since 1.0
 * @see CrudService
 */
public interface IArrivalNotificationService extends CrudService<ArrivalNotification, Long> {

  /**
   * Generates ArrivalNotifications out of the information contained from the event.
   *
   * @param arrivalEventDTO which contains the event information.
   * @return the generated ArrivalNotifications.
   * @throws ServiceException if an error occurred, while generating the notifications.
   */
  List<ArrivalNotification> generateFrom(ArrivalEventDTO arrivalEventDTO) throws ServiceException;
}
