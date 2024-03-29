package at.dse.g14.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
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
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String id;

  @NotBlank
  @Column(unique = true)
  private String name;

  @NotNull
  @OneToMany(mappedBy = "manufacturer")
  private Set<VehicleEntity> vehicles;
}
