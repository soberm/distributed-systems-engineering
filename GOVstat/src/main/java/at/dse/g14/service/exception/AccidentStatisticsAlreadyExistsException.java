package at.dse.g14.service.exception;

import at.dse.g14.commons.service.exception.ServiceException;

public class AccidentStatisticsAlreadyExistsException extends ServiceException {

  public AccidentStatisticsAlreadyExistsException() {
  }

  public AccidentStatisticsAlreadyExistsException(String message) {
    super(message);
  }

  public AccidentStatisticsAlreadyExistsException(String message, Exception e) {
    super(message, e);
  }
}
