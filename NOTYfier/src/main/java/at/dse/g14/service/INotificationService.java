package at.dse.g14.service;

import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.Notification;
import java.util.List;

public interface INotificationService {

  List<Notification> findAll() throws ServiceException;

  List<Notification> findByReceiver(String receiver) throws ServiceException;
}
