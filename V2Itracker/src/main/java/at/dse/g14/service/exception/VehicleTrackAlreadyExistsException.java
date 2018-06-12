package at.dse.g14.service.exception;

import at.dse.g14.commons.service.exception.ServiceException;

/**
 * An Exception, which is used, when an attempt to save an already existing VehicleTrack occurs.
 *
 * @author Michael Sober
 * @since 1.0
 * @see ServiceException
 */
public class VehicleTrackAlreadyExistsException extends ServiceException {

  public VehicleTrackAlreadyExistsException() {}

  public VehicleTrackAlreadyExistsException(String message) {
    super(message);
  }

  public VehicleTrackAlreadyExistsException(String message, Exception e) {
    super(message, e);
  }
}
