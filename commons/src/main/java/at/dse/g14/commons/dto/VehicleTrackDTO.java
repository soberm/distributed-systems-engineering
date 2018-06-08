package at.dse.g14.commons.dto;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTrackDTO implements DTO {

  private String id;

  @NotNull
  private String vin;

  @NotNull
  private String modelType;

  @Range(min = 0, max = 300)
  private Integer passengers;

  @NotNull
  @Size(min = 2, max = 2)
  private Double[] location;

  @DecimalMin(value = "0.0")
  @DecimalMax(value = "130.0")
  private BigDecimal speed;

  @DecimalMin(value = "0.0")
  private BigDecimal distanceVehicleAhead;

  @DecimalMin(value = "0.0")
  private BigDecimal distanceVehicleBehind;

  @NotNull
  private Boolean nearCrashEvent;

  @NotNull
  private Boolean crashEvent;

}
