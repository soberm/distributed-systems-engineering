package at.dse.g14.entity;

import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ClearanceNotification extends Notification {

  public ClearanceNotification(Long id, String receiver) {
    super(id, receiver);
  }
}
