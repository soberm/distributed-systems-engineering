package at.dse.g14.web;

import at.dse.g14.commons.dto.LiveData;
import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.commons.dto.VehicleManufacturer;
import at.dse.g14.persistence.VehicleManufacturerRepository;
import at.dse.g14.persistence.VehicleRepository;
import at.dse.g14.service.LiveDataService;
import at.dse.g14.service.exception.ValidationException;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@RestController
@RequestMapping("/manufacturer/{id}/vehicle/{vid}/livedata")
public class LiveDataController {

  private final LiveDataService dataService;
  private final VehicleRepository vehicleRepository;
  private final VehicleManufacturerRepository manufacturerRepository;

  @Autowired
  public LiveDataController(
      final LiveDataService dataService,
      final VehicleRepository vehicleRepository,
      final VehicleManufacturerRepository manufacturerRepository) {
    this.dataService = dataService;
    this.vehicleRepository = vehicleRepository;
    this.manufacturerRepository = manufacturerRepository;
  }

  @PostMapping
  public LiveData createVehicle(@RequestBody final LiveData data) throws ValidationException {
    return dataService.create(data);
  }

  @GetMapping("/{vid}")
  public ResponseEntity<Set<LiveData>> getVehicleData(
      @PathVariable("id") final long id, @PathVariable("vid") final long vid) {
    final Vehicle vehicle = vehicleRepository.findOne(vid);
    final VehicleManufacturer manufacturer = manufacturerRepository.findOne(id);

    return vehicle.getManufacturer().equals(manufacturer)
        ? new ResponseEntity<>(vehicle.getData(), HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }
}
