package at.dse.g14.persistence;

import at.dse.g14.entity.NearCrashEventNotification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NearCrashEventNotificationRepository
    extends CrudRepository<NearCrashEventNotification, Long> {}
