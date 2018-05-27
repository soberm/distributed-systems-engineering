package at.dse.g14.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccidentEventDTO {

    private LiveVehicleTrackDTO liveVehicleTrack;

    private List<String> vehiclesInRange;

}
