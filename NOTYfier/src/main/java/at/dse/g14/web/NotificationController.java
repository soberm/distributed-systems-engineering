package at.dse.g14.web;

import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.Notification;
import at.dse.g14.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * A Rest-Controller, which provides the necessary endpoints to retrieve notifications.
 *
 * @author Michael Sober
 * @since 1.0
 */
@RestController
@RequestMapping("/notification")
public class NotificationController {

  private final INotificationService notificationService;

  @Autowired
  public NotificationController(final INotificationService notificationService) {
    this.notificationService = notificationService;
  }

  /**
   * Finds all statistics found or for a specific receiver.
   *
   * @param receiver from which the notifications should be found.
   * @return the found notifications.
   * @throws ServiceException if an error finding the notifications occur.
   */
  @GetMapping
  public List<Notification> getNotifications(@RequestParam(required = false) String receiver)
      throws ServiceException {
    if (receiver == null || receiver.isEmpty()) {
      return notificationService.findAll();
    }
    return notificationService.findByReceiver(receiver);
  }
}
