package at.dse.g14.messaging;

import at.dse.g14.service.IVehicleTrackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Slf4j
@MessageEndpoint
public class VehicleTrackMessageHandler implements MessageHandler {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  private IVehicleTrackService vehicleTrackService;

  @Override
  @ServiceActivator(inputChannel = "vehicleTrackInputChannel")
  public void handleMessage(Message<?> message) throws MessagingException {
    log.info("VehicleTrackDTO-Message arrived! Payload: {}", message.getPayload());
    /*try {
      String payload = (String) message.getPayload();
      VehicleTrackDTO vehicleTrack = objectMapper.readValue(payload, VehicleTrackDTO.class);
      vehicleTrackService.update(vehicleTrack);*/
      AckReplyConsumer consumer =
          (AckReplyConsumer) message.getHeaders().get(GcpPubSubHeaders.ACKNOWLEDGEMENT);
      consumer.ack();
    /*} catch (ServiceException | IOException e) {
      log.error("Could not handle received message.", e);
    }*/
  }

}
