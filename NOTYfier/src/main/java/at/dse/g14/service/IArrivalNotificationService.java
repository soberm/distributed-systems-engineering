package at.dse.g14.service;

import at.dse.g14.commons.dto.ArrivalEventDTO;
import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.ArrivalNotification;

import java.util.List;

public interface IArrivalNotificationService extends CrudService<ArrivalNotification, Long> {

  List<ArrivalNotification> generateFrom(ArrivalEventDTO arrivalEventDTO) throws ServiceException;
}
