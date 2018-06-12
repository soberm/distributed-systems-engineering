package at.dse.g14.web.client.impl;

import at.dse.g14.commons.dto.data.Vehicle;
import at.dse.g14.web.client.VehicleDataClient;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VehicleDataClientFallback implements VehicleDataClient {

  @Override
  public List<Vehicle> getAllVehiclesOfManufacturer(String id) {
    return new ArrayList<>();
  }
}
