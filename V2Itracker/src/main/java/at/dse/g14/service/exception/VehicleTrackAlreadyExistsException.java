package at.dse.g14.service.exception;

public class VehicleTrackAlreadyExistsException extends ServiceException {

  public VehicleTrackAlreadyExistsException() {
  }

  public VehicleTrackAlreadyExistsException(String message) {
    super(message);
  }

  public VehicleTrackAlreadyExistsException(String message, Exception e) {
    super(message, e);
  }

}
