package at.dse.g14.persistence;

import at.dse.g14.entity.VehicleTrack;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleTrackRepository extends MongoRepository<VehicleTrack, Long> {

    List<VehicleTrack> findByLocationNear(Point p, Distance d);

}
