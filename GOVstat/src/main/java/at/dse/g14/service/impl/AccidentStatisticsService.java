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
      final Validator validator, final AccidentStatisticsRepository accidentStatisticsRepository) {
    this.validator = validator;
    this.accidentStatisticsRepository = accidentStatisticsRepository;
  }

  /**
   * Saves AccidentStatistics if they are not already existing.
   *
   * @param accidentStatistics which should be saved.
   * @return the saved AccidentStatistics.
   * @throws ServiceException if an error saving the AccidentStatistics occur.
   */
  @Override
  public AccidentStatistics save(AccidentStatistics accidentStatistics) throws ServiceException {
    validate(accidentStatistics);

    if (findOne(accidentStatistics.getId()) != null) {
      throw new AccidentStatisticsAlreadyExistsException(accidentStatistics + " already exists.");
    }

    log.info("Saving " + accidentStatistics);
    return accidentStatisticsRepository.save(accidentStatistics);
  }

  /**
   * Updates AccidentStatistics or will create them, if not already saved.
   *
   * @param accidentStatistics which should get updated.
   * @return the updated AccidentStatistics.
   * @throws ServiceException if an error updating the AccidentStatistics occur.
   */
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
    loadedAccidentStatistics.setArrivalTimeEmergencyService(
        accidentStatistics.getArrivalTimeEmergencyService());
    loadedAccidentStatistics.setClearanceTimeAccidentSpot(
        accidentStatistics.getClearanceTimeAccidentSpot());

    log.info("Updating " + loadedAccidentStatistics);
    return accidentStatisticsRepository.save(loadedAccidentStatistics);
  }

  /**
   * Deletes the AccidentStatistics with the provided id.
   *
   * @param id of the AccidentStatistics, which should get deleted.
   * @throws ServiceException if an error deleting the AccidentStatistics occur.
   */
  @Override
  public void delete(Long id) throws ServiceException {
    log.info("Deleted AccidentStatistics " + id);
    accidentStatisticsRepository.deleteById(id);
  }

  /**
   * Finds the AccidentStatistics with the provided id.
   *
   * @param id of the AccidentStatistics, which is searched.
   * @return the found AccidentStatistics or null if it is not found.
   * @throws ServiceException if an error finding the AccidentStatistics occur.
   */
  @Override
  public AccidentStatistics findOne(Long id) throws ServiceException {
    log.info("Finding AccidentStatistics " + id);
    if (id == null) {
      return null;
    }
    try {
      return accidentStatisticsRepository.findById(id).get();
    } catch (NoSuchElementException | IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * Finds all AccidentStatistics.
   *
   * @return the found AccidentStatistics or an empty list, if none are present.
   * @throws ServiceException if an error finding all AccidentStatistics occur.
   */
  @Override
  public List<AccidentStatistics> findAll() throws ServiceException {
    log.info("Finding all AccidentStatistics.");
    return (List<AccidentStatistics>) accidentStatisticsRepository.findAll();
  }

  /**
   * Validates AccidentStatistics.
   *
   * @param accidentStatistics which should be validated.
   * @throws ValidationException if the AccidentStatistics are not valid.
   */
  private void validate(AccidentStatistics accidentStatistics) throws ValidationException {
    log.debug("Validating " + accidentStatistics);
    if (!validator.validate(accidentStatistics).isEmpty()) {
      throw new ValidationException("AccidentStatistics not valid.");
    }
  }
}
