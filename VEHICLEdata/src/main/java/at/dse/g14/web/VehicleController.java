package at.dse.g14.web;

import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.commons.dto.VehicleManufacturer;
import at.dse.g14.persistence.VehicleManufacturerRepository;
import at.dse.g14.persistence.VehicleRepository;
import at.dse.g14.service.VehicleService;
import at.dse.g14.service.exception.ValidationException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@RestController
@RequestMapping("/manufacturer/{id}/vehicle")
public class VehicleController {

  private final VehicleService vehicleService;
  private final VehicleRepository vehicleRepository;
  private final VehicleManufacturerRepository manufacturerRepository;

  @Autowired
  public VehicleController(
      final VehicleService vehicleService,
      final VehicleRepository vehicleRepository,
      final VehicleManufacturerRepository manufacturerRepository) {
    this.vehicleService = vehicleService;
    this.vehicleRepository = vehicleRepository;
    this.manufacturerRepository = manufacturerRepository;
  }

  @PostMapping
  public Vehicle createVehicle(@RequestBody final Vehicle vehicle) throws ValidationException {
    return vehicleService.create(vehicle);
  }

  @GetMapping("/{vid}")
  public ResponseEntity<Vehicle> getVehicle(
      @PathVariable("id") final long id, @PathVariable("vid") final long vid) {
    final Vehicle vehicle = vehicleRepository.findOne(vid);
    final VehicleManufacturer manufacturer = manufacturerRepository.findOne(id);

    return vehicle.getManufacturer().equals(manufacturer)
        ? new ResponseEntity<>(vehicle, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @GetMapping
  public List<Vehicle> getAllVehicleOfManufacturer(@PathVariable("id") final long id) {
    return vehicleRepository.findAllByManufacturer_Id(id);
  }

  @PutMapping
  public Vehicle updateVehicle(@RequestBody final Vehicle vehicle) throws ValidationException {
    return vehicleService.update(vehicle);
  }
}
