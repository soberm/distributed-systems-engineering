package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.AccidentEventDTO;
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
import java.util.ArrayList;
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

  @Override
  public List<SpotlightNotification> generateFrom(AccidentEventDTO accidentEventDTO)
      throws ServiceException {
    validate(accidentEventDTO);
    log.info("Generating SpotlightNotifications for {}", accidentEventDTO);
    List<SpotlightNotification> spotlightNotifications = new ArrayList<>();
    for (String receiver : accidentEventDTO.getVehiclesInSmallRange()) {
      spotlightNotifications.add(new SpotlightNotification(null, receiver));
    }
    return spotlightNotifications;
  }

  private void validate(AccidentEventDTO accidentEventDTO) throws ValidationException {
    log.debug("Validating " + accidentEventDTO);
    if (!validator.validate(accidentEventDTO).isEmpty()) {
      throw new ValidationException("AccidentEventDTO not valid.");
    }
  }
}
