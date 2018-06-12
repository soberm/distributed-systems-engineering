package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.events.AccidentEventDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.SpeedNotification;
import at.dse.g14.persistence.SpeedNotificationRepository;
import at.dse.g14.service.AbstractCrudService;
import at.dse.g14.service.ISpeedNotificationService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  @Override
  public List<SpeedNotification> generateFrom(AccidentEventDTO accidentEventDTO)
      throws ServiceException {
    validate(accidentEventDTO);
    log.info("Generating SpeedNotifications for {}", accidentEventDTO);
    List<SpeedNotification> speedNotifications = new ArrayList<>();
    for (String receiver : accidentEventDTO.getVehiclesInSmallRange()) {
      speedNotifications.add(new SpeedNotification(null, receiver));
    }
    return speedNotifications;
  }

  private void validate(AccidentEventDTO accidentEventDTO) throws ValidationException {
    log.debug("Validating " + accidentEventDTO);
    if (!validator.validate(accidentEventDTO).isEmpty()) {
      throw new ValidationException("AccidentEventDTO not valid.");
    }
  }
}
