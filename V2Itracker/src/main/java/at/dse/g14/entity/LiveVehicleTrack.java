package at.dse.g14.entity;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * An entity which represents the latest information about a moving vehicle.
 *
 * @author Michael Sober
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "live_vehicle_tracks")
public class LiveVehicleTrack {

  @Id
  private String vin;

  @NotNull
  private String modelType;

  @Range(min = 0, max = 300)
  private Integer passengers;

  @NotNull
  @GeoSpatialIndexed(useGeneratedName = true)
  @Size(min = 2, max = 2)
  private Double[] location;

  @DecimalMin(value = "0.0")
  //    @DecimalMax(value = "130.0")
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
