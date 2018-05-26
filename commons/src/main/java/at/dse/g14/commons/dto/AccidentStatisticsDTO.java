package at.dse.g14.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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

    @Valid
    private GpsPoint location;

    @Range(min = 0, max = 300)
    private Integer passengers;

    @Min(0)
    private Integer arrivalTimeEmergencyService;

    @Min(0)
    private Integer clearanceTimeAccidentSpot;

}
