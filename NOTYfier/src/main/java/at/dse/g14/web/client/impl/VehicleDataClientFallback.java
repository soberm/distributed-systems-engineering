package at.dse.g14.web.client.impl;

import at.dse.g14.commons.dto.EmergencyService;
import at.dse.g14.commons.dto.VehicleManufacturer;
import at.dse.g14.web.client.VehicleDataClient;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class VehicleDataClientFallback implements VehicleDataClient {

  @Override
  public VehicleManufacturer getVehicleManufacturer(String vin) {
    return new VehicleManufacturer("1234", "VehicleManufacturer", null);
  }

  @Override
  public List<EmergencyService> getEmergencyServices() {
    return Arrays.asList(new EmergencyService("5678", "EmergencyService"));
  }
}
