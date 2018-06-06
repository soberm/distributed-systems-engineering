package at.dse.g14.persistence;

import at.dse.g14.entity.CrashEventNotification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrashEventNotificationRepository
    extends CrudRepository<CrashEventNotification, Long> {}
