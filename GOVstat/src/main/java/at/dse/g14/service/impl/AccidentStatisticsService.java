package at.dse.g14.service.impl;

import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.AccidentStatistics;
import at.dse.g14.persistence.AccidentStatisticsRepository;
import at.dse.g14.service.IAccidentStatisticsService;
import at.dse.g14.service.exception.AccidentStatisticsAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class AccidentStatisticsService implements IAccidentStatisticsService {

    private final Validator validator;
    private final AccidentStatisticsRepository accidentStatisticsRepository;

    @Autowired
    public AccidentStatisticsService(
            final Validator validator,
            final AccidentStatisticsRepository accidentStatisticsRepository) {
        this.validator = validator;
        this.accidentStatisticsRepository = accidentStatisticsRepository;
    }

    @Override
    public AccidentStatistics save(AccidentStatistics accidentStatistics) throws ServiceException {
        validate(accidentStatistics);

        if (findOne(accidentStatistics.getId()) != null) {
            throw new AccidentStatisticsAlreadyExistsException(accidentStatistics + " already exists.");
        }

        log.info("Saving " + accidentStatistics);
        return accidentStatisticsRepository.save(accidentStatistics);
    }

    @Override
    public AccidentStatistics update(AccidentStatistics accidentStatistics) throws ServiceException {
        validate(accidentStatistics);

        AccidentStatistics loadedAccidentStatistics = findOne(accidentStatistics.getId());

        if (loadedAccidentStatistics == null) {
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
        return accidentStatisticsRepository.save(loadedAccidentStatistics);
    }

    @Override
    public void delete(Long id) throws ServiceException {
        validate(id);
        log.info("Deleted AccidentStatisticsDTO " + id);
        accidentStatisticsRepository.deleteById(id);
    }

    @Override
    public AccidentStatistics findOne(Long id) throws ServiceException {
        validate(id);
        log.info("Finding AccidentStatisticsDTO " + id);
        try {
            return accidentStatisticsRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public List<AccidentStatistics> findAll() throws ServiceException {
        log.info("Finding all AccidentStatisticsDTO.");
        return (List<AccidentStatistics>) accidentStatisticsRepository.findAll();
    }

    private void validate(AccidentStatistics accidentStatistics) throws ValidationException {
        log.debug("Validating " + accidentStatistics);
        if (!validator.validate(accidentStatistics).isEmpty()) {
            throw new ValidationException("AccidentStatisticsDTO not valid.");
        }
    }

    private void validate(Long id) throws ValidationException {
        log.debug("Validating Id for AccidentStatisticsDTO " + id);
        if (id < 0) {
            throw new ValidationException("Id must be greater than 0.");
        }
    }

}
