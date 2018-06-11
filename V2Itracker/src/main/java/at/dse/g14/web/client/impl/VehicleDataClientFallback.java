package at.dse.g14.web.client.impl;

import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.web.client.VehicleDataClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleDataClientFallback implements VehicleDataClient {

    @Override
    public List<Vehicle> getAllVehiclesOfManufacturer(String id) {
        return new ArrayList<>();
    }

}
