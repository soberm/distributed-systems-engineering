package at.dse.g14.commons.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccidentStatisticsDTO {

    private Long id;

    @NotNull
    private String vin;

    @NotNull
    private String modelType;

  @NotNull
  @Size(min = 2, max = 2)
  private Double[] location;

    @Range(min = 0, max = 300)
    private Integer passengers;

    @Min(0)
    private Integer arrivalTimeEmergencyService;

    @Min(0)
    private Integer clearanceTimeAccidentSpot;

}
