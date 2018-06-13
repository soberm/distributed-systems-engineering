package at.dse.g14.service;

import at.dse.g14.commons.dto.events.AccidentEventDTO;
import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.NearCrashEventNotification;

import java.util.List;

/**
 * This interface represents the needed functionality around NearCrashEventNotifications.
 *
 * @author Michael Sober
 * @since 1.0
 * @see CrudService
 */
public interface INearCrashEventNotificationService
    extends CrudService<NearCrashEventNotification, Long> {

  /**
   * Generates NearCrashEventNotification out of the information contained from the event.
   *
   * @param accidentEventDTO which contains the event information.
   * @return the generated NearCrashEventNotifications.
   * @throws ServiceException if an error occurred, while generating the notifications.
   */
  List<NearCrashEventNotification> generateFrom(AccidentEventDTO accidentEventDTO)
      throws ServiceException;
}
