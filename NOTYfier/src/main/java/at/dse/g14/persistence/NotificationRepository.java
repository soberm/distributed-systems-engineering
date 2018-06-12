package at.dse.g14.persistence;

import at.dse.g14.entity.Notification;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

  List<Notification> findByReceiver(String receiver);
}
