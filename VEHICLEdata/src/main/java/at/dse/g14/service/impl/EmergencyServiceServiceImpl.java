package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.EmergencyService;
import at.dse.g14.entity.EmergencyServiceEntity;
import at.dse.g14.persistence.EmergencyServiceRepository;
import at.dse.g14.service.EmergencyServiceService;
import at.dse.g14.service.exception.ServiceException;
import at.dse.g14.service.exception.ValidationException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Service("emergencyServiceService")
public class EmergencyServiceServiceImpl implements EmergencyServiceService {

  private final EmergencyServiceRepository serviceRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public EmergencyServiceServiceImpl(
      final EmergencyServiceRepository serviceRepository, final ModelMapper modelMapper) {
    this.serviceRepository = serviceRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public EmergencyService save(final EmergencyService service) throws ServiceException {
    validate(service);
    final EmergencyServiceEntity entity = serviceRepository.save(convertToEntity(service));
    return convertToDto(entity);
  }

  @Override
  public EmergencyService update(final EmergencyService service) throws ServiceException {
    validate(service);
    if (service.getId() != null) {
      throw new ValidationException("No ID provided");
    }
    final EmergencyServiceEntity entity = serviceRepository.save(convertToEntity(service));
    return convertToDto(entity);
  }

  @Override
  public void delete(final Long serviceId) throws ServiceException {
    if (serviceId == null) {
      throw new ServiceException("ID is null!");
    }
    serviceRepository.delete(serviceId);
  }

  @Override
  public EmergencyService findOne(final Long serviceId) throws ServiceException {
    if (serviceId == null) {
      throw new ServiceException("ID is null!");
    }
    return convertToDto(serviceRepository.findOne(serviceId));
  }

  @Override
  public List<EmergencyService> findAll() throws ServiceException {
    return convertToDto((List<EmergencyServiceEntity>) serviceRepository.findAll());
  }

  private EmergencyService convertToDto(final EmergencyServiceEntity entity) {
    return modelMapper.map(entity, EmergencyService.class);
  }

  private List<EmergencyService> convertToDto(final List<EmergencyServiceEntity> entities) {
    return entities.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  private EmergencyServiceEntity convertToEntity(final EmergencyService dto) {
    return modelMapper.map(dto, EmergencyServiceEntity.class);
  }

  private void validate(final EmergencyService service) throws ValidationException {
  }
}
