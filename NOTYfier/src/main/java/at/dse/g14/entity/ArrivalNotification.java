package at.dse.g14.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * An entity which represents the notification, which informs stakeholders about an arrived
 * emergency service at the accident spot.
 *
 * @author Michael Sober
 * @since 1.0
 */
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ArrivalNotification extends Notification {

  public ArrivalNotification(Long id, String receiver) {
    super(id, receiver);
  }
}
