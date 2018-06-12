package at.dse.g14.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class NearCrashEventNotification extends Notification {

  private String vin;

  private String modelType;

  private Integer passengers;

  private Double[] location;

  private BigDecimal speed;

  private BigDecimal distanceVehicleAhead;

  private BigDecimal distanceVehicleBehind;

  @Builder
  public NearCrashEventNotification(
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
