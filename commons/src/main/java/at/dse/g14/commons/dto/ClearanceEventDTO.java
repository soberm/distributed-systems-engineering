package at.dse.g14.commons.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClearanceEventDTO {

  private Boolean cleared = true;
  private List<Vehicle> vehiclesToNotify;
}
