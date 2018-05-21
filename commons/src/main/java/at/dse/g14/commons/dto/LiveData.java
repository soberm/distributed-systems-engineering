package at.dse.g14.commons.dto;

import javax.persistence.Embedded;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveData {

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

  @NotNull
  private Vehicle vehicle;
}