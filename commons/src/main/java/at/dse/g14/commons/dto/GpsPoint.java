package at.dse.g14.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
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
