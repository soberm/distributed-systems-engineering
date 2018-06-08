package at.dse.g14.persistence;

import at.dse.g14.entity.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

  List<Notification> findByReceiver(String receiver);
}
