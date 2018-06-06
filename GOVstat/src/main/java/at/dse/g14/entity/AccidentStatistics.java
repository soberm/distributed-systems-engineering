package at.dse.g14.entity;

import at.dse.g14.commons.dto.GpsPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Entity
@Table(name = "accident_statistics")
@AllArgsConstructor
@NoArgsConstructor
public class AccidentStatistics {

    @Id
    private Long id;

    @NotNull
    private String vin;

    @NotNull
    private String modelType;

    @Valid
    @Embedded
    private GpsPoint location;

    @Range(min = 0, max = 300)
    private Integer passengers;

    @Min(0)
    private Integer arrivalTimeEmergencyService;

    @Min(0)
    private Integer clearanceTimeAccidentSpot;

}
