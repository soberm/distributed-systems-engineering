package at.dse.g14.service;

import at.dse.g14.commons.dto.AccidentEventDTO;
import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.SpotlightNotification;

import java.util.List;

public interface ISpotlightNotificationService extends CrudService<SpotlightNotification, Long> {

  List<SpotlightNotification> generateFrom(AccidentEventDTO accidentEventDTO)
      throws ServiceException;
}
