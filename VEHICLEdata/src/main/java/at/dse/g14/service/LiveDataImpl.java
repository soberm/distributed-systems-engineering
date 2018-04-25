package at.dse.g14.service;

import at.dse.g14.commons.dto.LiveData;
import at.dse.g14.entity.LiveDataEntity;
import at.dse.g14.persistence.LiveDataRepository;
import at.dse.g14.service.exception.ValidationException;
import org.modelmapper.ModelMapper;
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
  private final ModelMapper modelMapper;

  @Autowired
  public LiveDataImpl(final LiveDataRepository dataRepository, final ModelMapper modelMapper) {
    this.dataRepository = dataRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public LiveData create(final LiveData data) throws ValidationException {
    validate(data);
    LiveDataEntity entity = dataRepository.save(convertToEntity(data));
    return convertToDto(entity);
  }

  private void validate(final LiveData data) throws ValidationException {
    if (data.getPosition() == null) {
      throw new ValidationException("No GPS point provided");
    }
  }

  private LiveData convertToDto(LiveDataEntity entity) {
    return modelMapper.map(entity, LiveData.class);
  }

  private LiveDataEntity convertToEntity(LiveData dto) {
    return modelMapper.map(dto, LiveDataEntity.class);
  }
}
