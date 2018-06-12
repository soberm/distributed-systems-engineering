package at.dse.g14.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
