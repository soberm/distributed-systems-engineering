package at.dse.g14.service;

import at.dse.g14.commons.dto.LiveData;
import at.dse.g14.persistence.LiveDataRepository;
import at.dse.g14.service.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Service
public class LiveDataImpl implements LiveDataService {

  private final LiveDataRepository dataRepository;

  @Autowired
  public LiveDataImpl(LiveDataRepository dataRepository) {
    this.dataRepository = dataRepository;
  }

  @Override
  public LiveData create(final LiveData data) throws ValidationException {
    validate(data);
    return dataRepository.save(data);
  }

  private void validate(final LiveData data) throws ValidationException {
    if (data.getPosition() == null) {
      throw new ValidationException("No GPS point provided");
    }
  }
}
