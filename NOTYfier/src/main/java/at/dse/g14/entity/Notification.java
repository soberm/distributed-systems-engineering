package at.dse.g14.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String receiver;
}
