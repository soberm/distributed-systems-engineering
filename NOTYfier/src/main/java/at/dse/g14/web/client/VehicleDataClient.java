package at.dse.g14.web.client;

import at.dse.g14.commons.dto.EmergencyService;
import at.dse.g14.commons.dto.VehicleManufacturer;
import at.dse.g14.web.client.impl.VehicleDataClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(
  name = "vehicledata-service",
  url = "${vehicledata.address}",
  fallback = VehicleDataClientFallback.class
)
public interface VehicleDataClient {

  @RequestMapping(
    method = RequestMethod.GET,
    value = "/vehicle/{vin}",
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  VehicleManufacturer getVehicleManufacturer(@PathVariable("vin") String vin);

  @RequestMapping(
    method = RequestMethod.GET,
    value = "/emergencyService",
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  List<EmergencyService> getEmergencyServices();
}
