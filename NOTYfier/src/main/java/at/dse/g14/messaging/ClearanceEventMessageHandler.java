package at.dse.g14.messaging;

import at.dse.g14.commons.dto.events.ClearanceEventDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.service.IClearanceNotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Slf4j
@MessageEndpoint
public class ClearanceEventMessageHandler implements MessageHandler {

  private final ObjectMapper objectMapper;
  private final IClearanceNotificationService clearanceNotificationService;

  public ClearanceEventMessageHandler(
      ObjectMapper objectMapper, IClearanceNotificationService clearanceNotificationService) {
    this.objectMapper = objectMapper;
    this.clearanceNotificationService = clearanceNotificationService;
  }

  @Override
  @ServiceActivator(inputChannel = "clearanceEventInputChannel")
  public void handleMessage(Message<?> message) throws MessagingException {
    log.info("ClearanceEvent-Message arrived! Payload: {}", message.getPayload());
    String payload = (String) message.getPayload();
    AckReplyConsumer consumer =
        (AckReplyConsumer) message.getHeaders().get(GcpPubSubHeaders.ACKNOWLEDGEMENT);
    try {
      ClearanceEventDTO clearanceEventDTO =
          objectMapper.readValue(payload, ClearanceEventDTO.class);
      handleClearanceEvent(clearanceEventDTO);
    } catch (Exception e) {
      log.error("Could not handle ClearanceEvent-Message. Message ignored.", e);
    }
    if (consumer != null) {
      consumer.ack();
    }
  }

  private void handleClearanceEvent(ClearanceEventDTO clearanceEventDTO) throws ServiceException {
    log.info("Handling {}", clearanceEventDTO);
    // TODO: Handle ArrivalEvent
  }
}
