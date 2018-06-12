package at.dse.g14.commons.dto.data;

import at.dse.g14.commons.dto.DTO;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Vehicle implements DTO {

  private String vin;

  @NotBlank private String modelType;

  private VehicleManufacturer manufacturer;
}
