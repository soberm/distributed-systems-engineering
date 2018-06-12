package at.dse.g14.commons.dto.data;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
// @AllArgsConstructor
public class EmergencyService {

  private String id;

  @NotBlank private String name;

  public EmergencyService(String id, String name) {
    this.id = id;
    this.name = name;
  }
}
