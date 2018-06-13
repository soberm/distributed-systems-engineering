package at.dse.g14.service;

import at.dse.g14.commons.dto.events.AccidentEventDTO;
import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.SpeedNotification;

import java.util.List;

/**
 * This interface represents the needed functionality around SpeedNotifications.
 *
 * @author Michael Sober
 * @since 1.0
 * @see CrudService
 */
public interface ISpeedNotificationService extends CrudService<SpeedNotification, Long> {

  /**
   * Generates SpeedNotification out of the information contained from the event.
   *
   * @param accidentEventDTO which contains the event information.
   * @return the generated SpeedNotification.
   * @throws ServiceException if an error occurred, while generating the notifications.
   */
  List<SpeedNotification> generateFrom(AccidentEventDTO accidentEventDTO) throws ServiceException;
}
