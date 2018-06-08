package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.SpotlightEventDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.SpotlightNotification;
import at.dse.g14.persistence.SpotlightNotificationRepository;
import at.dse.g14.service.AbstractCrudService;
import at.dse.g14.service.ISpotlightNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;

@Slf4j
@Service
public class SpotlightNotificationService extends AbstractCrudService<SpotlightNotification, Long>
    implements ISpotlightNotificationService {

  @Autowired
  public SpotlightNotificationService(
      final Validator validator,
      final SpotlightNotificationRepository spotlightNotificationRepository) {
    super(spotlightNotificationRepository, validator);
  }

  @Override
  protected SpotlightNotification updateLoadedEntity(
      SpotlightNotification loadedSpotlightNotification,
      SpotlightNotification spotlightNotification) {
    loadedSpotlightNotification.setReceiver(spotlightNotification.getReceiver());
    return loadedSpotlightNotification;
  }

  private void validate(SpotlightEventDTO spotlightEventDTO) throws ValidationException {
    log.debug("Validating " + spotlightEventDTO);
    if (!validator.validate(spotlightEventDTO).isEmpty()) {
      throw new ValidationException("SpotlightEventDTO not valid.");
    }
  }

  @Override
  public List<SpotlightNotification> generateFrom(SpotlightEventDTO spotlightEventDTO) throws ServiceException {
    return null;
  }
}
