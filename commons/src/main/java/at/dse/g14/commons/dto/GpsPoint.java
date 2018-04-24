package at.dse.g14.commons.dto;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@Embeddable
public class GpsPoint {

  private double lat;
  private double lon;

}
