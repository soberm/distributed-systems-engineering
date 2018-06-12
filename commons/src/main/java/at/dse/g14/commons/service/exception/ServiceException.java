package at.dse.g14.commons.service.exception;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
public class ServiceException extends Exception {

  public ServiceException() {
  }

  public ServiceException(final String message) {
    super(message);
  }

  public ServiceException(final String message, final Exception e) {
    super(message, e);
  }

  public ServiceException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
