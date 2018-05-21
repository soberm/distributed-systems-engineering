package at.dse.g14.persistence;

import at.dse.g14.entity.VehicleTrackEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTrackRepository extends MongoRepository<VehicleTrackEntity, Long> {

}
