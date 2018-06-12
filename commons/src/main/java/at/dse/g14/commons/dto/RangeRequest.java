package at.dse.g14.commons.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RangeRequest {

  private Double[] location;

  private BigDecimal rangeKilometre;
}
