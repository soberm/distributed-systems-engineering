package at.dse.g14.web.client.impl;

import at.dse.g14.commons.dto.data.EmergencyService;
import at.dse.g14.commons.dto.data.VehicleManufacturer;
import at.dse.g14.web.client.VehicleDataClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Fallback if the vehicle data service is not available.
 *
 * @author Michael Sober
 * @since 1.0
 * @see VehicleDataClient
 */
@Component
public class VehicleDataClientFallback implements VehicleDataClient {

  @Override
  public VehicleManufacturer getVehicleManufacturer(String vin) {
    return new VehicleManufacturer("0000", "VehicleManufacturer");
  }

  @Override
  public List<EmergencyService> getEmergencyServices() {
    return new ArrayList<>();
  }
}
