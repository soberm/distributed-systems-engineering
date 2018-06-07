package at.dse.g14.data.simulator;

import at.dse.g14.commons.dto.EmergencyService;
import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.commons.dto.VehicleManufacturer;
import at.dse.g14.commons.dto.VehicleTrackDTO;
import at.dse.g14.data.simulator.config.PubSubConfig.VehicleTrackOutboundGateway;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Slf4j
@Component
public class DseSender {

  private final VehicleTrackOutboundGateway gateway;
  private final ObjectMapper objectMapper;
  private final RestTemplate restTemplate;

  @Autowired
  public DseSender(
      final VehicleTrackOutboundGateway gateway,
      final ObjectMapper objectMapper,
      final RestTemplateBuilder restTemplateBuilder) {

    this.restTemplate = restTemplateBuilder.build();
    this.gateway = gateway;
    this.objectMapper = objectMapper;
  }

  public Vehicle createVehicle(final Vehicle vehicle) {
    log.info("create: {}", vehicle);

    return restTemplate
        .postForObject("http://localhost:8001/manufacturer/{id}/vehicle",
            vehicle,
            Vehicle.class,
            vehicle.getManufacturer().getId());

  }

  public VehicleManufacturer createManufacturer(final VehicleManufacturer manufacturer) {
    log.info("create: {}", manufacturer);

    return restTemplate.postForObject("http://localhost:8001/manufacturer/",
        manufacturer,
        VehicleManufacturer.class);
  }

  public EmergencyService createEmergencyService(final EmergencyService emergencyService) {
    log.info("create: {}", emergencyService);

    return restTemplate.postForObject("http://localhost:8001/emergencyService/",
        emergencyService,
        EmergencyService.class);
  }

  public void sendTrackData(final VehicleTrackDTO vehicleTrack) {
    log.info("send: {}", vehicleTrack);
    try {
      gateway.sendToPubsub(objectMapper.writeValueAsString(vehicleTrack));
    } catch (JsonProcessingException e) {
      log.error("Could not send LiveVehicleTrackDTO to PubSub.", e);
    }
  }

}