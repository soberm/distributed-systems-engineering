package at.dse.g14.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Table(name = "vehicle")
@javax.persistence.Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity implements Entity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  @NotBlank
  private String modelType;

  @ManyToOne
  @JoinColumn(name = "manufacturer_id", nullable = false)
  private VehicleManufacturerEntity manufacturer;
}
