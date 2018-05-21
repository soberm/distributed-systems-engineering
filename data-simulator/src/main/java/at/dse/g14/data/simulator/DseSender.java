package at.dse.g14.data.simulator;

import at.dse.g14.commons.dto.EmergencyService;
import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.commons.dto.VehicleManufacturer;
import at.dse.g14.commons.dto.VehicleTrack;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Slf4j
public class DseSender {

  public void createVehicle(final Vehicle vehicle) {
    log.info("create: {}", vehicle);
  }

  public void createManufacturer(final VehicleManufacturer manufacturer) {
    log.info("create: {}", manufacturer);
  }

  public void createEmergencyService(final EmergencyService emergencyService) {
    log.info("create: {}", emergencyService);
  }

  public void sendTrackData(final VehicleTrack vehicleTrack) {
    log.info("send: {}", vehicleTrack);
  }

}
