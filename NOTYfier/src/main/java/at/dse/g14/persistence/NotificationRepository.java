package at.dse.g14.persistence;

import at.dse.g14.entity.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to manage notifications.
 *
 * @author Michael Sober
 * @since 1.0
 * @see CrudRepository
 */
@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

  List<Notification> findByReceiver(String receiver);

  List<Notification> findFirst10ByReceiverOrderByDateDesc(String receiver);
}
