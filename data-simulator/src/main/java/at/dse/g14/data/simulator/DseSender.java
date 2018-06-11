package at.dse.g14.data.simulator;

import at.dse.g14.commons.dto.ArrivalEventDTO;
import at.dse.g14.commons.dto.ClearanceEventDTO;
import at.dse.g14.commons.dto.EmergencyService;
import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.commons.dto.VehicleManufacturer;
import at.dse.g14.commons.dto.VehicleTrackDTO;
import at.dse.g14.data.simulator.config.PubSubConfig.ArrivalEventOutboundGateway;
import at.dse.g14.data.simulator.config.PubSubConfig.ClearanceEventOutboundGateway;
import at.dse.g14.data.simulator.config.PubSubConfig.VehicleTrackOutboundGateway;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

  private final ClearanceEventOutboundGateway clearanceEventOutboundGateway;
  private final ArrivalEventOutboundGateway arrivalEventOutboundGateway;
  private final VehicleTrackOutboundGateway vehicleTrackOutboundGateway;
  private final ObjectMapper objectMapper;
  private final RestTemplate restTemplate;

  @Value("${vehicledata.address}")
  private String vehicledataAddress;

  @Autowired
  public DseSender(
      final ClearanceEventOutboundGateway clearanceEventOutboundGateway,
      final VehicleTrackOutboundGateway vehicleTrackOutboundGateway,
      final ArrivalEventOutboundGateway arrivalEventOutboundGateway,
      final ObjectMapper objectMapper,
      final RestTemplateBuilder restTemplateBuilder) {

    this.clearanceEventOutboundGateway = clearanceEventOutboundGateway;
    this.arrivalEventOutboundGateway = arrivalEventOutboundGateway;
    this.vehicleTrackOutboundGateway = vehicleTrackOutboundGateway;

    this.restTemplate = restTemplateBuilder.build();
    this.objectMapper = objectMapper;
  }

  public Vehicle createVehicle(final Vehicle vehicle) {
    log.info("create: {}", vehicle);

    return restTemplate.postForObject(
        vehicledataAddress + "/manufacturer/{vin}/vehicle",
        vehicle,
        Vehicle.class,
        vehicle.getManufacturer().getId());
  }

  public List<Vehicle> getVehiclesOfManufacturer(final VehicleManufacturer manufacturer) {
    log.info("getVehiclesOfManufacturer: {}", manufacturer);

    final Vehicle[] vehicles =
        restTemplate.getForObject(
            vehicledataAddress + "/manufacturer/{vin}/vehicle",
            Vehicle[].class,
            manufacturer.getId());

    return Arrays.asList(vehicles);
  }

  public VehicleManufacturer createManufacturer(final VehicleManufacturer manufacturer) {
    log.info("create: {}", manufacturer);

    return restTemplate.postForObject(
        vehicledataAddress + "/manufacturer/", manufacturer, VehicleManufacturer.class);
  }

  public VehicleManufacturer getManufacturer(final VehicleManufacturer manufacturer) {
    log.info("getManufacturer: {}", manufacturer);
    return restTemplate.getForObject(
        vehicledataAddress + "/manufacturer/" + manufacturer.getId(), VehicleManufacturer.class);
  }

  public EmergencyService createEmergencyService(final EmergencyService emergencyService) {
    log.info("create: {}", emergencyService);

    return restTemplate.postForObject(
        vehicledataAddress + "/emergencyService/", emergencyService, EmergencyService.class);
  }

  public void sendTrackData(final VehicleTrackDTO vehicleTrack) {
    log.info("send: {}", vehicleTrack);
    try {
      vehicleTrackOutboundGateway.sendToPubsub(objectMapper.writeValueAsString(vehicleTrack));
    } catch (JsonProcessingException e) {
      log.error("Could not send LiveVehicleTrackDTO to PubSub.", e);
    }
  }

  public void sendEvent(final ArrivalEventDTO arrivalEvent) {
    log.info("send event {}", arrivalEvent);
    try {
      arrivalEventOutboundGateway.sendToPubsub(objectMapper.writeValueAsString(arrivalEvent));
    } catch (JsonProcessingException e) {
      log.error("Could not send ArrivalEventDTO to PubSub.", e);
    }
  }

  public void sendEvent(final ClearanceEventDTO clearanceEvent) {
    log.info("send event {}", clearanceEvent);
    try {
      clearanceEventOutboundGateway.sendToPubsub(objectMapper.writeValueAsString(clearanceEvent));
    } catch (JsonProcessingException e) {
      log.error("Could not send ClearanceEventDTO to PubSub.", e);
    }
  }
}
