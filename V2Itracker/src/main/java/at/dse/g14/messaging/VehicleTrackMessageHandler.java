package at.dse.g14.messaging;

import at.dse.g14.commons.dto.track.VehicleTrackDTO;
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

/**
 * A MessageHandler which retrieves VehicleTrack-Messages from the configured Google Pub/Sub topic
 * and handles them.
 *
 * @author Michael Sober
 * @since 1.0
 * @see MessageHandler
 */
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

  /**
   * Handles an VehicleTrack-Message, by saving it.
   *
   * @param message which contains the payload with the VehicleTrack.
   * @throws MessagingException if an error occurs, while retrieving the message.
   */
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

  /**
   * Converts an VehicleTrack dto to an entity.
   * @param vehicleTrackDTO which should be converted to an entity.
   * @return the converted dto.
   */
  private VehicleTrack convertToEntity(VehicleTrackDTO vehicleTrackDTO) {
    return modelMapper.map(vehicleTrackDTO, VehicleTrack.class);
  }
}
