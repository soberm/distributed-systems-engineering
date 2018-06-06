package at.dse.g14.persistence;

import at.dse.g14.entity.ArrivalNotification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArrivalNotificationRepository extends CrudRepository<ArrivalNotification, Long> {}
