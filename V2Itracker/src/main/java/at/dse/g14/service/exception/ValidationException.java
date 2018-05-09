package at.dse.g14.service.exception;

public class ValidationException extends ServiceException {

  public ValidationException() {
  }

  public ValidationException(String message) {
    super(message);
  }

  public ValidationException(String message, Exception e) {
    super(message, e);
  }

}
