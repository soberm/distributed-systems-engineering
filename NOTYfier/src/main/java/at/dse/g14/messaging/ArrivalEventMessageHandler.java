package at.dse.g14.messaging;

import at.dse.g14.commons.dto.events.ArrivalEventDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.ArrivalNotification;
import at.dse.g14.service.IArrivalNotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.List;

@Slf4j
@MessageEndpoint
public class ArrivalEventMessageHandler implements MessageHandler {

  private final ObjectMapper objectMapper;
  private final IArrivalNotificationService arrivalNotificationService;

  public ArrivalEventMessageHandler(
      ObjectMapper objectMapper, IArrivalNotificationService arrivalNotificationService) {
    this.objectMapper = objectMapper;
    this.arrivalNotificationService = arrivalNotificationService;
  }

  @Override
  @ServiceActivator(inputChannel = "arrivalEventInputChannel")
  public void handleMessage(Message<?> message) throws MessagingException {
    log.info("ArrivalEvent-Message arrived! Payload: {}", message.getPayload());
    String payload = (String) message.getPayload();
    AckReplyConsumer consumer =
        (AckReplyConsumer) message.getHeaders().get(GcpPubSubHeaders.ACKNOWLEDGEMENT);
    try {
      ArrivalEventDTO arrivalEventDTO = objectMapper.readValue(payload, ArrivalEventDTO.class);
      handleArrivalEvent(arrivalEventDTO);
    } catch (Exception e) {
      log.error("Could not handle ArrivalEvent-Message. Message ignored.", e);
    }
    if (consumer != null) {
      consumer.ack();
    }
  }

  private void handleArrivalEvent(ArrivalEventDTO arrivalEventDTO) throws ServiceException {
    log.info("Handling ArrivalEvent of {}", arrivalEventDTO);
    List<ArrivalNotification> arrivalNotifications =
        arrivalNotificationService.generateFrom(arrivalEventDTO);
    for (ArrivalNotification arrivalNotification : arrivalNotifications) {
      arrivalNotificationService.update(arrivalNotification);
    }
  }
}
