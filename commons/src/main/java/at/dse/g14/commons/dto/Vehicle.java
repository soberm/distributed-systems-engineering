package at.dse.g14.commons.dto;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Data
@Entity
@Builder
public class Vehicle implements DTO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String modelType;
  private int passenger;
  @Embedded private GpsPoint position;
  private double speed;
  private double distanceVehicleAhead;
  private double distanceVehicleBehind;

  @ManyToOne
  @JoinColumn(name = "manufacturer_id", nullable = false)
  private VehicleManufacturer manufacturer;

  @Builder.Default private boolean nearCrashEvent = false;
  @Builder.Default private boolean crashEvent = false;
}
