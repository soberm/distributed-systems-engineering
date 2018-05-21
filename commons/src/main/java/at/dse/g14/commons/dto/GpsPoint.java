package at.dse.g14.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GpsPoint {

  private double lat;
  private double lon;

}