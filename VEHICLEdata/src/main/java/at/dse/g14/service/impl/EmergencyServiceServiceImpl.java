package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.data.EmergencyService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.EmergencyServiceEntity;
import at.dse.g14.persistence.EmergencyServiceRepository;
import at.dse.g14.service.EmergencyServiceService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Service("emergencyServiceService")
@Slf4j
public class EmergencyServiceServiceImpl implements EmergencyServiceService {

  private final EmergencyServiceRepository serviceRepository;
  private final ModelMapper modelMapper;
  private final Validator validator;

  @Autowired
  public EmergencyServiceServiceImpl(
      final EmergencyServiceRepository serviceRepository,
      final ModelMapper modelMapper,
      final Validator validator) {
    this.serviceRepository = serviceRepository;
    this.modelMapper = modelMapper;
    this.validator = validator;
  }

  @Override
  public EmergencyService save(final EmergencyService service) throws ServiceException {
    validate(service);

    if (service.getId() != null) {
      return service;
    } else {
      final EmergencyService found = getByName(service.getName());
      if (found != null) {
        return found;
      }
    }

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
  public void delete(final String serviceId) throws ServiceException {
    if (serviceId == null) {
      throw new ServiceException("ID is null!");
    }
    serviceRepository.deleteById(serviceId);
  }

  @Override
  public EmergencyService findOne(final String serviceId) throws ServiceException {
    if (serviceId == null) {
      throw new ServiceException("ID is null!");
    }
    final Optional<EmergencyServiceEntity> foundService = serviceRepository.findById(serviceId);
    if (!foundService.isPresent()) {
      throw new ServiceException("Unknown serviceId " + serviceId);
    }
    return convertToDto(foundService.get());
  }

  @Override
  public List<EmergencyService> findAll() {
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
    log.debug("Validating " + service);
    Set<ConstraintViolation<EmergencyService>> violations = validator.validate(service);
    if (!violations.isEmpty()) {
      throw new ValidationException(
          "EmergencyService not valid: \n"
              + Arrays.toString(violations.stream().map(Object::toString).toArray()));
    }
  }

  @Override
  public EmergencyService getByName(final String name) {
    final EmergencyServiceEntity entity = serviceRepository.getByName(name);
    return (entity != null) ? convertToDto(entity) : null;
  }
}
