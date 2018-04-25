package at.dse.g14.entity;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Data
@javax.persistence.Entity
@Builder
@Table(name = "live_data")
@NoArgsConstructor
@AllArgsConstructor
public class LiveDataEntity implements Entity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Min(0)
  private int passenger;

  @Embedded
  private GpsPoint position;

  @Min(0)
  private double speed;

  @Min(0)
  private double distanceVehicleAhead;

  @Min(0)
  private double distanceVehicleBehind;

  @Builder.Default
  private boolean nearCrashEvent = false;
  @Builder.Default
  private boolean crashEvent = false;

  @ManyToOne
  @JoinColumn(name = "vehicle_id", nullable = false)
  @NotNull
  private VehicleEntity vehicle;
}
