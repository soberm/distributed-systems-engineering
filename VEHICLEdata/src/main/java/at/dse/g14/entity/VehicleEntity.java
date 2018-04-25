package at.dse.g14.entity;

import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity implements Entity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String modelType;

  @ManyToOne
  @JoinColumn(name = "manufacturer_id", nullable = false)
  private VehicleManufacturerEntity manufacturer;

  @NotNull
  @OneToMany(mappedBy = "vehicle")
  private Set<LiveDataEntity> data;
}
