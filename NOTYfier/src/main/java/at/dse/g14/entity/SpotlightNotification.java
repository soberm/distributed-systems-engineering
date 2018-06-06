package at.dse.g14.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SpotlightNotification extends Notification {

  public SpotlightNotification(Long id, String receiver) {
    super(id, receiver);
  }
}
