package at.dse.g14.service;

import at.dse.g14.commons.dto.events.AccidentEventDTO;
import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.CrashEventNotification;
import java.util.List;

public interface ICrashEventNotificationService extends CrudService<CrashEventNotification, Long> {

  List<CrashEventNotification> generateFrom(AccidentEventDTO accidentEventDTO)
      throws ServiceException;
}
