package at.dse.g14.messaging;

import at.dse.g14.commons.dto.AccidentStatisticsDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.AccidentStatistics;
import at.dse.g14.service.IAccidentStatisticsService;
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

import java.io.IOException;

/**
 * A MessageHandler which retrieves AccidentStatistic-Messages from the configured Google Pub/Sub
 * topic and handles them.
 *
 * @author Michael Sober
 * @since 1.0
 * @see MessageHandler
 */
@Slf4j
@MessageEndpoint
public class AccidentStatisticsMessageHandler implements MessageHandler {

  private final ObjectMapper objectMapper;
  private final ModelMapper modelMapper;
  private final IAccidentStatisticsService accidentStatisticsService;

  public AccidentStatisticsMessageHandler(
      ObjectMapper objectMapper,
      ModelMapper modelMapper,
      IAccidentStatisticsService accidentStatisticsService) {
    this.objectMapper = objectMapper;
    this.modelMapper = modelMapper;
    this.accidentStatisticsService = accidentStatisticsService;
  }

  /**
   * Handles an AccidentStatistics-Message. If the AccidentStatistics already exist, it will be
   * updated.
   *
   * @param message which contains the payload with the AccidentStatistics.
   * @throws MessagingException if an error occurs, while retrieving the message.
   */
  @Override
  @ServiceActivator(inputChannel = "accidentStatisticsChannel")
  public void handleMessage(Message<?> message) throws MessagingException {
    log.info("AccidentStatistics-Message arrived! Payload: {}", message.getPayload());
    String payload = (String) message.getPayload();
    AckReplyConsumer consumer =
        (AckReplyConsumer) message.getHeaders().get(GcpPubSubHeaders.ACKNOWLEDGEMENT);
    try {
      AccidentStatisticsDTO accidentStatisticsDTO =
          objectMapper.readValue(payload, AccidentStatisticsDTO.class);
      accidentStatisticsService.update(convertToEntity(accidentStatisticsDTO));
    } catch (ServiceException | IOException e) {
      log.error("Could not handle AccidentStatistics-Message. Message ignored.", e);
    }
    if (consumer != null) {
      consumer.ack();
    }
  }

  /**
   * Converts an AccidentStatisticsDTO to an AccidentStatics entity.
   *
   * @param accidentStatisticsDTO which should get converted to an entity.
   * @return the entity, which got created from mapping the dto.
   */
  private AccidentStatistics convertToEntity(AccidentStatisticsDTO accidentStatisticsDTO) {
    return modelMapper.map(accidentStatisticsDTO, AccidentStatistics.class);
  }
}
