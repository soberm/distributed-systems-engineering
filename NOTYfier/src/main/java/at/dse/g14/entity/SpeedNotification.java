package at.dse.g14.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * An entity which represents a notification, which tells a vehicle to reduce their speed.
 *
 * @author Michael Sober
 * @since 1.0
 */
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SpeedNotification extends Notification {

  public SpeedNotification(Long id, String receiver) {
    super(id, receiver);
  }
}
