package at.dse.g14.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * An entity which represents the notification, which informs stakeholders about a cleared accident
 * spot.
 *
 * @author Michael Sober
 * @since 1.0
 */
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ClearanceNotification extends Notification {

  public ClearanceNotification(Long id, String receiver) {
    super(id, receiver);
  }
}
