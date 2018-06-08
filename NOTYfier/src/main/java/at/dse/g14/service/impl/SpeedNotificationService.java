package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.SpeedEventDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.SpeedNotification;
import at.dse.g14.persistence.SpeedNotificationRepository;
import at.dse.g14.service.AbstractCrudService;
import at.dse.g14.service.ISpeedNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;

@Slf4j
@Service
public class SpeedNotificationService extends AbstractCrudService<SpeedNotification, Long>
    implements ISpeedNotificationService {

  @Autowired
  public SpeedNotificationService(
      final Validator validator, final SpeedNotificationRepository speedNotificationRepository) {
    super(speedNotificationRepository, validator);
  }

  @Override
  protected SpeedNotification updateLoadedEntity(
      SpeedNotification loadedSpeedNotification, SpeedNotification speedNotification) {
    loadedSpeedNotification.setReceiver(speedNotification.getReceiver());
    return loadedSpeedNotification;
  }

  private void validate(SpeedEventDTO speedEventDTO) throws ValidationException {
    log.debug("Validating " + speedEventDTO);
    if (!validator.validate(speedEventDTO).isEmpty()) {
      throw new ValidationException("SpeedEventDTO not valid.");
    }
  }

  @Override
  public List<SpeedNotification> generateFrom(SpeedEventDTO speedEventDTO) throws ServiceException {
    return null;
  }
}
