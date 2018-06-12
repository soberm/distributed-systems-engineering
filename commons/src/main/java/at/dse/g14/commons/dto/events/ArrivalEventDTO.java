package at.dse.g14.commons.dto.events;

import at.dse.g14.commons.dto.data.Vehicle;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArrivalEventDTO {

    private List<Vehicle> vehiclesToNotify;
}
