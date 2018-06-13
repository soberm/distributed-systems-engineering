package at.dse.g14.service.exception;

import at.dse.g14.commons.service.exception.ServiceException;

/**
 * An Exception, which is used, when an attempt to save an already existing AccidentStatistics
 * occurs.
 *
 * @author Michael Sober
 * @since 1.0
 * @see ServiceException
 */
public class AccidentStatisticsAlreadyExistsException extends ServiceException {

  public AccidentStatisticsAlreadyExistsException() {}

  public AccidentStatisticsAlreadyExistsException(String message) {
    super(message);
  }

  public AccidentStatisticsAlreadyExistsException(String message, Exception e) {
    super(message, e);
  }
}
