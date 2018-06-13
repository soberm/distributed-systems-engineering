package at.dse.g14.service.exception;

import at.dse.g14.commons.service.exception.ServiceException;

/**
 * An Exception, which is used, when an attempt to save an already existing Entity occurs.
 *
 * @author Michael Sober
 * @since 1.0
 * @see ServiceException
 */
public class EntityAlreadyExistsException extends ServiceException {

  public EntityAlreadyExistsException() {}

  public EntityAlreadyExistsException(String message) {
    super(message);
  }

  public EntityAlreadyExistsException(String message, Exception e) {
    super(message, e);
  }
}
