package at.dse.g14.service.exception;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
public class ValidationException extends ServiceException {

  public ValidationException() {
  }

  public ValidationException(final String message) {
    super(message);
  }

  public ValidationException(final String message, final Exception e) {
    super(message, e);
  }

  public ValidationException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
