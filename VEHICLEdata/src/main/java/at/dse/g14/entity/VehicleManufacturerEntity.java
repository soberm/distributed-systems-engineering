package at.dse.g14.entity;

import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
@javax.persistence.Entity
@Table(name = "vehicle_manufacturer")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleManufacturerEntity implements Entity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @NotBlank
  private String name;

  @NotNull
  @OneToMany(mappedBy = "manufacturer")
  private Set<VehicleEntity> vehicles;
}
