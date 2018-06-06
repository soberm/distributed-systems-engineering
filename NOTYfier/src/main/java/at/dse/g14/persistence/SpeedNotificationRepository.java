package at.dse.g14.persistence;

import at.dse.g14.entity.SpeedNotification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeedNotificationRepository extends CrudRepository<SpeedNotification, Long> {}
