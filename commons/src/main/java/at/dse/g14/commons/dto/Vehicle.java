package at.dse.g14.commons.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle implements DTO {

  private Long id;

  @NotEmpty
  protected Set<LiveData> data;

  private VehicleManufacturer manufacturer;
  @NotBlank
  private String modelType;
}
