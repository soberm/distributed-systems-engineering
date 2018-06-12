package at.dse.g14.service.exception;

import at.dse.g14.commons.service.exception.ServiceException;

public class EntityAlreadyExistsException extends ServiceException {

  public EntityAlreadyExistsException() {
  }

  public EntityAlreadyExistsException(String message) {
    super(message);
  }

  public EntityAlreadyExistsException(String message, Exception e) {
    super(message, e);
  }
}
