package at.dse.g14.service.exception;

public class ServiceException extends Exception {

  public ServiceException() {
  }

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Exception e) {
    super(message, e);
  }

}
