package at.dse.g14.entity;

import at.dse.g14.commons.dto.GpsPoint;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "vehicle_tracks")
public class VehicleTrack {

  @Id
  private Long id;

  @NotNull
  private String vin;

  @NotNull
  private String modelType;

  @Range(min = 0, max = 300)
  private Integer passengers;

  @Valid
  private GpsPoint location;

  @DecimalMin(value = "0.0")
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
