package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.ClearanceEventDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.ClearanceNotification;
import at.dse.g14.persistence.ClearanceNotificationRepository;
import at.dse.g14.service.AbstractCrudService;
import at.dse.g14.service.IClearanceNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;

@Slf4j
@Service
public class ClearanceNotificationService extends AbstractCrudService<ClearanceNotification, Long>
    implements IClearanceNotificationService {

  @Autowired
  public ClearanceNotificationService(
      final Validator validator,
      final ClearanceNotificationRepository clearanceNotificationRepository) {
    super(clearanceNotificationRepository, validator);
  }

  @Override
  protected ClearanceNotification updateLoadedEntity(
      ClearanceNotification loadedClearanceNotification,
      ClearanceNotification clearanceNotification) {
    loadedClearanceNotification.setReceiver(clearanceNotification.getReceiver());
    return loadedClearanceNotification;
  }

  private void validate(ClearanceEventDTO clearanceEventDTO) throws ValidationException {
    log.debug("Validating " + clearanceEventDTO);
    if (!validator.validate(clearanceEventDTO).isEmpty()) {
      throw new ValidationException("ClearanceEventDTO not valid.");
    }
  }

  @Override
  public List<ClearanceNotification> generateFrom(ClearanceEventDTO clearanceEventDTO) throws ServiceException {
    //TODO: Generate notification or notifications from DTO
    return null;
  }
}
