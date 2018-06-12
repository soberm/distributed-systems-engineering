package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.events.ArrivalEventDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.ArrivalNotification;
import at.dse.g14.persistence.ArrivalNotificationRepository;
import at.dse.g14.service.AbstractCrudService;
import at.dse.g14.service.IArrivalNotificationService;
import java.util.List;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ArrivalNotificationService extends AbstractCrudService<ArrivalNotification, Long>
    implements IArrivalNotificationService {

  @Autowired
  public ArrivalNotificationService(
      final Validator validator,
      final ArrivalNotificationRepository arrivalNotificationRepository) {
    super(arrivalNotificationRepository, validator);
  }

  @Override
  protected ArrivalNotification updateLoadedEntity(
      ArrivalNotification loadedArrivalNotification, ArrivalNotification arrivalNotification) {
    loadedArrivalNotification.setReceiver(arrivalNotification.getReceiver());
    return loadedArrivalNotification;
  }

  private void validate(ArrivalEventDTO arrivalEventDTO) throws ValidationException {
    log.debug("Validating " + arrivalEventDTO);
    if (!validator.validate(arrivalEventDTO).isEmpty()) {
      throw new ValidationException("ArrivalEventDTO not valid.");
    }
  }

  @Override
  public List<ArrivalNotification> generateFrom(ArrivalEventDTO arrivalEventDTO)
      throws ServiceException {
    // TODO: Generate notification or notifications from DTO
    return null;
  }
}
