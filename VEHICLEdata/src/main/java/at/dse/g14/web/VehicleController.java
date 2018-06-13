package at.dse.g14.web;

import at.dse.g14.commons.dto.data.Vehicle;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@RestController
@RequestMapping("/manufacturer/{id}/vehicle")
public class VehicleController {

  private final VehicleService vehicleService;

  @Autowired
  public VehicleController(final VehicleService vehicleService) {
    this.vehicleService = vehicleService;
  }

  @PostMapping
  public Vehicle createVehicle(@RequestBody final Vehicle vehicle) throws ServiceException {
    return vehicleService.save(vehicle);
  }

  @GetMapping("/{vid}")
  public Vehicle getVehicle(
      @PathVariable("id") final String id, @PathVariable("vid") final String vid)
      throws ServiceException {
    return vehicleService.findOne(id, vid);
  }

  @GetMapping
  public List<Vehicle> getAllVehicleOfManufacturer(@PathVariable("id") final String id) {
    return vehicleService.findAllOfManufacturer(id);
  }

  @PutMapping
  public Vehicle updateVehicle(@RequestBody final Vehicle vehicle) throws ServiceException {
    return vehicleService.update(vehicle);
  }
}
