package at.dse.g14.persistence;

import at.dse.g14.entity.ClearanceNotification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to manage ClearanceNotifications.
 *
 * @author Michael Sober
 * @since 1.0
 * @see CrudRepository
 */
@Repository
public interface ClearanceNotificationRepository
    extends CrudRepository<ClearanceNotification, Long> {}
