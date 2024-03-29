package at.dse.g14.entity;

import lombok.*;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * An entity which represents the notification, which informs stakeholders about a crash that
 * occurred.
 *
 * @author Michael Sober
 * @since 1.0
 */
@Data
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CrashEventNotification extends Notification {

  private String vin;

  private String modelType;

  private Integer passengers;

  private Double[] location;

  private BigDecimal speed;

  private BigDecimal distanceVehicleAhead;

  private BigDecimal distanceVehicleBehind;

  @Builder
  public CrashEventNotification(
      Long id,
      String receiver,
      String vin,
      String modelType,
      Integer passengers,
      Double[] location,
      BigDecimal speed,
      BigDecimal distanceVehicleAhead,
      BigDecimal distanceVehicleBehind) {
    super(id, receiver);
    this.vin = vin;
    this.modelType = modelType;
    this.passengers = passengers;
    this.location = location;
    this.speed = speed;
    this.distanceVehicleAhead = distanceVehicleAhead;
    this.distanceVehicleBehind = distanceVehicleBehind;
  }
}
