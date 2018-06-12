package at.dse.g14.web.client;

import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.web.client.impl.VehicleDataClientFallback;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
    name = "vehicledata-service",
    url = "${vehicledata.address}",
    fallback = VehicleDataClientFallback.class)
public interface VehicleDataClient {

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/manufacturer/{id}/vehicle",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  List<Vehicle> getAllVehiclesOfManufacturer(@PathVariable("id") final String id);
}
