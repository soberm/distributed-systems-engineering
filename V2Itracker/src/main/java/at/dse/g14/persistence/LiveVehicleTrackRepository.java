package at.dse.g14.persistence;

import at.dse.g14.entity.LiveVehicleTrack;
import java.util.List;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveVehicleTrackRepository extends MongoRepository<LiveVehicleTrack, String> {

  List<LiveVehicleTrack> findByLocationNear(Point p, Distance d);
}
