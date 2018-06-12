package at.dse.g14.messaging;

import at.dse.g14.commons.dto.VehicleTrackDTO;
import at.dse.g14.entity.VehicleTrack;
import at.dse.g14.service.IVehicleTrackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Slf4j
@MessageEndpoint
public class VehicleTrackMessageHandler implements MessageHandler {

  private final ObjectMapper objectMapper;
  private final ModelMapper modelMapper;
  private final IVehicleTrackService vehicleTrackService;

  public VehicleTrackMessageHandler(
      ObjectMapper objectMapper,
      ModelMapper modelMapper,
      IVehicleTrackService vehicleTrackService) {
    this.objectMapper = objectMapper;
    this.modelMapper = modelMapper;
    this.vehicleTrackService = vehicleTrackService;
  }

  @Override
  @ServiceActivator(inputChannel = "vehicleTrackInputChannel")
  public void handleMessage(Message<?> message) throws MessagingException {
    log.info("VehicleTrack-Message arrived! Payload: {}", message.getPayload());
    String payload = (String) message.getPayload();
    AckReplyConsumer consumer =
        (AckReplyConsumer) message.getHeaders().get(GcpPubSubHeaders.ACKNOWLEDGEMENT);
    try {
      VehicleTrackDTO vehicleTrackDTO = objectMapper.readValue(payload, VehicleTrackDTO.class);
      vehicleTrackService.update(convertToEntity(vehicleTrackDTO));
    } catch (Exception e) {
      log.error("Could not handle VehicleTrack-Message. Message ignored.", e);
    }
    if (consumer != null) {
      consumer.ack();
    }
  }

  private VehicleTrack convertToEntity(VehicleTrackDTO vehicleTrackDTO) {
    return modelMapper.map(vehicleTrackDTO, VehicleTrack.class);
  }
}
