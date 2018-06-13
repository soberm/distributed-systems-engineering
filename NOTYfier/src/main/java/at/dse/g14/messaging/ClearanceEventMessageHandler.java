package at.dse.g14.messaging;

import at.dse.g14.commons.dto.events.ClearanceEventDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.ClearanceNotification;
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

import java.util.List;

/**
 * A MessageHandler which retrieves ClearanceEvent-Messages from the configured Google Pub/Sub topic
 * and handles them.
 *
 * @author Michael Sober
 * @since 1.0
 * @see MessageHandler
 */
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

  /**
   * Handles an ClearanceEvent, by generating all notifications.
   *
   * @param message which contains the payload with the ClearanceEvent.
   * @throws MessagingException if an error occurs, while retrieving the message.
   */
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

  /**
   * Generates and saves the messages for the ClearanceEvent.
   *
   * @param clearanceEventDTO which should be handled.
   * @throws ServiceException if an error, while generating and saving the notification occurs.
   */
  private void handleClearanceEvent(ClearanceEventDTO clearanceEventDTO) throws ServiceException {
    log.info("Handling {}", clearanceEventDTO);
    List<ClearanceNotification> clearanceNotifications =
        clearanceNotificationService.generateFrom(clearanceEventDTO);
    for (ClearanceNotification clearanceNotification : clearanceNotifications) {
      clearanceNotificationService.update(clearanceNotification);
    }
  }
}
