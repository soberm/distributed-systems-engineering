package at.dse.g14.commons.dto;

import java.util.Set;
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
//@AllArgsConstructor
public class VehicleManufacturer implements DTO {

  private String id;

  @NotBlank
  private String name;

  @NotEmpty
  private Set<Vehicle> vehicles;

  public VehicleManufacturer(String id, String name,
      Set<Vehicle> vehicles) {
    this.id = id;
    this.name = name;
    this.vehicles = vehicles;
  }
}
