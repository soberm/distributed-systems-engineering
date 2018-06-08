package at.dse.g14.service.impl;

import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.Notification;
import at.dse.g14.persistence.NotificationRepository;
import at.dse.g14.service.INotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NotificationService implements INotificationService {

  private final NotificationRepository notificationRepository;

  @Autowired
  public NotificationService(final NotificationRepository notificationRepository) {
    this.notificationRepository = notificationRepository;
  }

  @Override
  public List<Notification> findAll() throws ServiceException {
    log.info("Finding all notifications.");
    return (List<Notification>) notificationRepository.findAll();
  }

  @Override
  public List<Notification> findByReceiver(String receiver) throws ServiceException {
    log.info("Finding all notifications for {}", receiver);
    return notificationRepository.findByReceiver(receiver);
  }
}
