package at.dse.g14.entity;

import at.dse.g14.commons.dto.GpsPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * An entity which represents the statistics of an accident.
 *
 * @author Michael Sober
 * @since 1.0
 */
@Data
@Builder
@Entity
@Table(name = "accident_statistics")
@AllArgsConstructor
@NoArgsConstructor
public class AccidentStatistics {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull private String vin;

  @NotNull private String modelType;

  @Valid @Embedded private GpsPoint location;

  @Range(min = 0, max = 300)
  private Integer passengers;

  @Min(0)
  private Integer arrivalTimeEmergencyService;

  @Min(0)
  private Integer clearanceTimeAccidentSpot;
}
