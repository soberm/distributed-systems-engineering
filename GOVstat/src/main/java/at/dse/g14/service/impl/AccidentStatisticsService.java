package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.AccidentStatistics;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.AccidentStatisticsEntity;
import at.dse.g14.persistence.AccidentStatisticsRepository;
import at.dse.g14.service.IAccidentStatisticsService;
import at.dse.g14.service.exception.AccidentStatisticsAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccidentStatisticsService implements IAccidentStatisticsService {

    private final Validator validator;
    private final AccidentStatisticsRepository accidentStatisticsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AccidentStatisticsService(
            final Validator validator,
            final AccidentStatisticsRepository accidentStatisticsRepository,
            final ModelMapper modelMapper) {
        this.validator = validator;
        this.accidentStatisticsRepository = accidentStatisticsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AccidentStatistics save(AccidentStatistics accidentStatistics) throws ServiceException {
        validate(accidentStatistics);

        if (findOne(accidentStatistics.getId()) != null) {
            throw new AccidentStatisticsAlreadyExistsException(accidentStatistics + " already exists.");
        }

        log.info("Saving " + accidentStatistics);
        return convertToDto(accidentStatisticsRepository.save(convertToEntity(accidentStatistics)));
    }

    @Override
    public AccidentStatistics update(AccidentStatistics accidentStatistics) throws ServiceException {
        validate(accidentStatistics);

        AccidentStatistics loadedAccidentStatistics = findOne(accidentStatistics.getId());

        if (accidentStatistics == null) {
            log.info(accidentStatistics + " does not exist. Creating a new one.");
            return save(accidentStatistics);
        }

        loadedAccidentStatistics.setVin(accidentStatistics.getVin());
        loadedAccidentStatistics.setModelType(accidentStatistics.getModelType());
        loadedAccidentStatistics.setLocation(accidentStatistics.getLocation());
        loadedAccidentStatistics.setPassengers(accidentStatistics.getPassengers());
        loadedAccidentStatistics.setArrivalTimeEmergencyService(accidentStatistics.getArrivalTimeEmergencyService());
        loadedAccidentStatistics.setClearanceTimeAccidentSpot(accidentStatistics.getClearanceTimeAccidentSpot());

        log.info("Updating " + loadedAccidentStatistics);
        return convertToDto(accidentStatisticsRepository.save(convertToEntity(loadedAccidentStatistics)));
    }

    @Override
    public void delete(Long id) throws ServiceException {
        validate(id);
        log.info("Deleted AccidentStatistics " + id);
        accidentStatisticsRepository.deleteById(id);
    }

    @Override
    public AccidentStatistics findOne(Long id) throws ServiceException {
        validate(id);
        log.info("Finding AccidentStatistics " + id);
        try {
            return convertToDto(accidentStatisticsRepository.findById(id).get());
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public List<AccidentStatistics> findAll() throws ServiceException {
        log.info("Finding all AccidentStatistics.");
        return convertToDto((List<AccidentStatisticsEntity>) accidentStatisticsRepository.findAll());
    }

    private void validate(AccidentStatistics accidentStatistics) throws ValidationException {
        log.debug("Validating " + accidentStatistics);
        if (!validator.validate(accidentStatistics).isEmpty()) {
            throw new ValidationException("AccidentStatistics not valid.");
        }
    }

    private void validate(Long id) throws ValidationException {
        log.debug("Validating Id for AccidentStatistics " + id);
        if (id < 0) {
            throw new ValidationException("Id must be greater than 0.");
        }
    }

    private AccidentStatistics convertToDto(AccidentStatisticsEntity accidentStatisticsEntity) {
        return modelMapper.map(accidentStatisticsEntity, AccidentStatistics.class);
    }

    private List<AccidentStatistics> convertToDto(final List<AccidentStatisticsEntity> entities) {
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private AccidentStatisticsEntity convertToEntity(AccidentStatistics dto) {
        return modelMapper.map(dto, AccidentStatisticsEntity.class);
    }

    private List<AccidentStatisticsEntity> convertToEntity(final List<AccidentStatistics> entities) {
        return entities.stream().map(this::convertToEntity).collect(Collectors.toList());
    }

}
