package at.dse.g14.service.exception;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
public class ValidationException extends Exception {

  public ValidationException(final String message) {
    super(message);
  }

  public ValidationException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
