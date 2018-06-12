package at.dse.g14.service.exception;

import at.dse.g14.commons.service.exception.ServiceException;

/**
 * An Exception, which is used, when an attempt to save an already existing LiveVehicleTrack
 * occurs.
 *
 * @author Michael Sober
 * @since 1.0
 * @see ServiceException
 */
public class LiveVehicleTrackAlreadyExistsException extends ServiceException {

  public LiveVehicleTrackAlreadyExistsException() {
  }

  public LiveVehicleTrackAlreadyExistsException(String message) {
    super(message);
  }

  public LiveVehicleTrackAlreadyExistsException(String message, Exception e) {
    super(message, e);
  }
}
