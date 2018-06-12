package at.dse.g14.web;

import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.Notification;
import at.dse.g14.service.INotificationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

  private final INotificationService notificationService;

  @Autowired
  public NotificationController(final INotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @GetMapping
  public List<Notification> getNotifications(@RequestParam(required = false) String receiver)
      throws ServiceException {
    // TODO: Map to DTOs and the DTOs need a type to distinguish different message types
    if (receiver == null || receiver.isEmpty()) {
      return notificationService.findAll();
    }
    return notificationService.findByReceiver(receiver);
  }
}
