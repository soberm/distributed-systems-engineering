package at.dse.g14.commons.dto;

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
//@AllArgsConstructor
public class EmergencyService {

  private Long id;

  @NotBlank
  private String name;

  public EmergencyService(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
