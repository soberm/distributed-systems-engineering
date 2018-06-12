package at.dse.g14.commons.dto;

import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccidentEventDTO {

  @Valid
  private LiveVehicleTrackDTO liveVehicleTrack;

  private List<String> vehiclesInBigRange;

  private List<String> vehiclesInSmallRange;
}
