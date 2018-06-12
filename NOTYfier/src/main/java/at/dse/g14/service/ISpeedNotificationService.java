package at.dse.g14.service;

import at.dse.g14.commons.dto.events.AccidentEventDTO;
import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.SpeedNotification;
import java.util.List;

public interface ISpeedNotificationService extends CrudService<SpeedNotification, Long> {

  List<SpeedNotification> generateFrom(AccidentEventDTO accidentEventDTO) throws ServiceException;
}
