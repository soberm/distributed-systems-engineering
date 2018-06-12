package at.dse.g14.service;

import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.LiveVehicleTrack;
import java.util.List;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

/**
 * This interface represents the needed functionality around LiveVehicleTracks.
 *
 * @author Michael Sober
 * @since 1.0
 * @see CrudService
 */
public interface ILiveVehicleTrackService extends CrudService<LiveVehicleTrack, String> {

  /**
   * Finds all LiveVehicleTracks which are in the right distance of a specific point.
   * @param p the point from which the distance is checked.
   * @param d the distance which should be checked.
   * @return all LiveVehicleTracks found in this area.
   * @throws ServiceException if an error finding the LiveVehicleTracks occur.
   */
  List<LiveVehicleTrack> findByLocationNear(Point p, Distance d) throws ServiceException;

  /**
   * Finding all LiveVehicleTracks which belong to vehicles of a specific manufacturer.
   * @param id of the manufacturer.
   * @return the found LiveVehicleTracks for the manufacturer.
   * @throws ServiceException if an error finding the LiveVehicleTracks occur.
   */
  List<LiveVehicleTrack> findByManufacturer(String id) throws ServiceException;
}
