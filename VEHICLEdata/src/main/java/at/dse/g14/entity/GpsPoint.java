package at.dse.g14.entity;

import javax.persistence.Embeddable;
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
@Embeddable
public class GpsPoint {

  private double lat;
  private double lon;

}
