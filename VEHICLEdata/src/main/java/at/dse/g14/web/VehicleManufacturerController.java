package at.dse.g14.web;

import at.dse.g14.commons.dto.data.VehicleManufacturer;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.service.VehicleManufacturerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@RestController
@RequestMapping("/manufacturer/")
public class VehicleManufacturerController {

  private final VehicleManufacturerService manufacturerService;

  @Autowired
  public VehicleManufacturerController(final VehicleManufacturerService manufacturerService) {
    this.manufacturerService = manufacturerService;
  }

  @PostMapping
  public VehicleManufacturer createVehicleManufacturer(
      @RequestBody final VehicleManufacturer manufacturer) throws ServiceException {
    return manufacturerService.save(manufacturer);
  }

  @GetMapping("/{id}")
  public VehicleManufacturer getVehicleManufacturer(@PathVariable("id") final String id)
      throws ServiceException {
    return manufacturerService.findOne(id);
  }

  @GetMapping
  public VehicleManufacturer getVehicleManufacturerByVid(@RequestParam("vid") final String vin)
      throws ServiceException {
    return manufacturerService.findByVin(vin);
  }

  @GetMapping("getAll")
  public List<VehicleManufacturer> getAllVehicleManufacturers() throws ServiceException {
    return manufacturerService.findAll();
  }

  @PutMapping
  public VehicleManufacturer updateVehicleManufacturer(
      @RequestBody final VehicleManufacturer manufacturer) throws ServiceException {
    return manufacturerService.update(manufacturer);
  }
}
