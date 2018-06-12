package at.dse.g14.service;

import at.dse.g14.commons.dto.events.ClearanceEventDTO;
import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.ClearanceNotification;
import java.util.List;

public interface IClearanceNotificationService extends CrudService<ClearanceNotification, Long> {

  List<ClearanceNotification> generateFrom(ClearanceEventDTO clearanceEventDTO)
      throws ServiceException;
}
