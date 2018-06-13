package at.dse.g14.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * An entity which represents a notification, by providing the necessary information.
 *
 * @author Michael Sober
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public abstract class Notification implements at.dse.g14.entity.Entity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String receiver;

  private LocalDateTime date;

  public Notification(Long id, String receiver) {
    this.id = id;
    this.receiver = receiver;
    this.date = LocalDateTime.now();
  }
}
