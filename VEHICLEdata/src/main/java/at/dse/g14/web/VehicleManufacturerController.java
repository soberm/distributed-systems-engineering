package at.dse.g14.web;

import at.dse.g14.commons.dto.VehicleManufacturer;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.service.VehicleManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
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

  @GetMapping("/{vid}")
  public VehicleManufacturer getVehicleManufacturer(@PathVariable("id") final String id)
      throws ServiceException {
    return manufacturerService.findOne(id);
  }

  @PutMapping
  public VehicleManufacturer updateVehicleManufacturer(
      @RequestBody final VehicleManufacturer manufacturer) throws ServiceException {
    return manufacturerService.update(manufacturer);
  }
}
