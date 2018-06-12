package at.dse.g14.persistence;

import at.dse.g14.entity.SpotlightNotification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to manage SpotlightNotifications.
 *
 * @author Michael Sober
 * @since 1.0
 * @see CrudRepository
 */
@Repository
public interface SpotlightNotificationRepository
    extends CrudRepository<SpotlightNotification, Long> {}
