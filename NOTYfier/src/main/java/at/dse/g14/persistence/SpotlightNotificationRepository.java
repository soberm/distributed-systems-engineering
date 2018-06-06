package at.dse.g14.persistence;

import at.dse.g14.entity.SpotlightNotification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotlightNotificationRepository
    extends CrudRepository<SpotlightNotification, Long> {}
