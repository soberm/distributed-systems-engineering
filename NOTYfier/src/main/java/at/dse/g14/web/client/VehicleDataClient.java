package at.dse.g14.web.client;

import at.dse.g14.commons.dto.data.EmergencyService;
import at.dse.g14.commons.dto.data.VehicleManufacturer;
import at.dse.g14.web.client.impl.VehicleDataClientFallback;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "vehicledata-service",
    url = "${vehicledata.address}",
    fallback = VehicleDataClientFallback.class)
public interface VehicleDataClient {

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/manufacturer",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  VehicleManufacturer getVehicleManufacturer(@RequestParam("vin") String vin);

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/emergencyService",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  List<EmergencyService> getEmergencyServices();
}
