package at.dse.g14.web;

import at.dse.g14.commons.dto.EmergencyService;
import at.dse.g14.service.EmergencyServiceService;
import at.dse.g14.service.exception.ServiceException;
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
@RequestMapping("/emergencyService")
public class EmergencyServiceController {

  private final EmergencyServiceService emergencyServiceService;

  @Autowired
  public EmergencyServiceController(final EmergencyServiceService emergencyServiceService) {
    this.emergencyServiceService = emergencyServiceService;
  }

  @PostMapping
  public EmergencyService createEmergencyService(
      @RequestBody final EmergencyService emergencyService)
      throws ServiceException {
    return emergencyServiceService.save(emergencyService);
  }

  @GetMapping("/{vid}")
  public EmergencyService getEmergencyService(@PathVariable("id") final long id)
      throws ServiceException {
    return emergencyServiceService.findOne(id);
  }

  @PutMapping
  public EmergencyService updateEmergencyService(
      @RequestBody final EmergencyService emergencyService)
      throws ServiceException {
    return emergencyServiceService.update(emergencyService);
  }
}
