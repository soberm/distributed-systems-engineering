package at.dse.g14.service;

import at.dse.g14.commons.dto.AccidentEventDTO;
import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.NearCrashEventNotification;

import java.util.List;

public interface INearCrashEventNotificationService
    extends CrudService<NearCrashEventNotification, Long> {

  List<NearCrashEventNotification> generateFrom(AccidentEventDTO accidentEventDTO)
      throws ServiceException;
}
