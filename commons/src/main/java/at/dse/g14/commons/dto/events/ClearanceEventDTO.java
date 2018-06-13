package at.dse.g14.commons.dto.events;

import at.dse.g14.commons.dto.data.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClearanceEventDTO {

  private List<Vehicle> vehiclesToNotify;
}
