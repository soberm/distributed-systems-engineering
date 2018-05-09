package at.dse.g14.entity;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GpsPoint {

  @DecimalMin(value = "-90.0")
  @DecimalMax(value = "+90.0")
  private BigDecimal lat;

  @DecimalMin(value = "-180.0")
  @DecimalMax(value = "+180.0")
  private BigDecimal lon;

}
