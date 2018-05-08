package at.dse.g14.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "vehicle_tracks")
public class VehicleTrack {

  @Id
  private Long id;

  private String vin;

  private String modelType;

  private int passengers;

  private GpsPoint location;

  private double speed;

  private double distanceVehicleAhead;

  private double distanceVehicleBehind;

  private boolean nearCrashEvent;

  private boolean crashEvent;

}
