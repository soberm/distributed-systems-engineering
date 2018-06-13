package at.dse.g14.service;

import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.Notification;

import java.util.List;

/**
 * This interface represents the needed functionality around Notifications.
 *
 * @author Michael Sober
 * @since 1.0
 * @see CrudService
 */
public interface INotificationService {

  /**
   * Retrieves all notifications.
   *
   * @return all found notifications.
   * @throws ServiceException if an error while, finding the notifications occurred.
   */
  List<Notification> findAll() throws ServiceException;

  /**
   * Retrieves the top 10 notifications for the specified receiver.
   * @param receiver of which the top 10 notifications should be found.
   * @return the notifications found.
   * @throws ServiceException if an error while, finding the notifications occurred.
   */
  List<Notification> findTop10ByReceiver(String receiver) throws ServiceException;

  /**
   * Retrieves all notifications of a given receiver.
   *
   * @param receiver of which the notifications should be found.
   * @return the found notifications of the receiver.
   * @throws ServiceException if an error finding the notifications occurred.
   */
  List<Notification> findByReceiver(String receiver) throws ServiceException;
}
