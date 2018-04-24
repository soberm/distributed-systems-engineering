package at.dse.g14.commons.dto;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle implements DTO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String modelType;

  @Min(0)
  private int passenger;

  @Embedded private GpsPoint position;

  @Min(0)
  private double speed;

  @Min(0)
  private double distanceVehicleAhead;

  @Min(0)
  private double distanceVehicleBehind;

  @ManyToOne
  @JoinColumn(name = "manufacturer_id", nullable = false)
  private VehicleManufacturer manufacturer;

  @Builder.Default private boolean nearCrashEvent = false;
  @Builder.Default private boolean crashEvent = false;
}
