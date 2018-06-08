package at.dse.g14.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ClearanceNotification extends Notification {

  public ClearanceNotification(Long id, String receiver) {
    super(id, receiver);
  }
}
