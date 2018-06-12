package at.dse.g14.persistence;

import at.dse.g14.entity.LiveVehicleTrack;
import java.util.List;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to manage LiveVehicleTracks.
 *
 * @author Michael Sober
 * @since 1.0
 * @see MongoRepository
 */
@Repository
public interface LiveVehicleTrackRepository extends MongoRepository<LiveVehicleTrack, String> {

  /**
   * Finds all LiveVehicleTracks which are in the right distance of a specific point.
   * @param p the point from which the distance is checked.
   * @param d the distance which should be checked.
   * @return all LiveVehicleTracks found in this area.
   */
  List<LiveVehicleTrack> findByLocationNear(Point p, Distance d);
}
