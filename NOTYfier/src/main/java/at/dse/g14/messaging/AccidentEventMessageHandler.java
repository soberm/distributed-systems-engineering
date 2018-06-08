package at.dse.g14.messaging;

import at.dse.g14.commons.dto.AccidentEventDTO;
import at.dse.g14.commons.dto.LiveVehicleTrackDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.CrashEventNotification;
import at.dse.g14.entity.NearCrashEventNotification;
import at.dse.g14.entity.SpeedNotification;
import at.dse.g14.entity.SpotlightNotification;
import at.dse.g14.service.ICrashEventNotificationService;
import at.dse.g14.service.INearCrashEventNotificationService;
import at.dse.g14.service.ISpeedNotificationService;
import at.dse.g14.service.ISpotlightNotificationService;
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
public class AccidentEventMessageHandler implements MessageHandler {

  private final ObjectMapper objectMapper;
  private final ICrashEventNotificationService crashEventNotificationService;
  private final INearCrashEventNotificationService nearCrashEventNotificationService;
  private final ISpotlightNotificationService spotlightNotificationService;
  private final ISpeedNotificationService speedNotificationService;

  public AccidentEventMessageHandler(
      ObjectMapper objectMapper,
      ICrashEventNotificationService crashEventNotificationService,
      INearCrashEventNotificationService nearCrashEventNotificationService,
      ISpotlightNotificationService spotlightNotificationService,
      ISpeedNotificationService speedNotificationService) {
    this.objectMapper = objectMapper;
    this.crashEventNotificationService = crashEventNotificationService;
    this.nearCrashEventNotificationService = nearCrashEventNotificationService;
    this.spotlightNotificationService = spotlightNotificationService;
    this.speedNotificationService = speedNotificationService;
  }

  @Override
  @ServiceActivator(inputChannel = "accidentEventInputChannel")
  public void handleMessage(Message<?> message) throws MessagingException {
    log.info("AccidentEvent-Message arrived! Payload: {}", message.getPayload());
    String payload = (String) message.getPayload();
    AckReplyConsumer consumer =
        (AckReplyConsumer) message.getHeaders().get(GcpPubSubHeaders.ACKNOWLEDGEMENT);
    try {
      AccidentEventDTO accidentEventDTO = objectMapper.readValue(payload, AccidentEventDTO.class);
      LiveVehicleTrackDTO liveVehicleTrackDTO = accidentEventDTO.getLiveVehicleTrack();
      if (liveVehicleTrackDTO.getCrashEvent()) {
        handleCrashEvent(accidentEventDTO);
      } else if (liveVehicleTrackDTO.getNearCrashEvent()) {
        handleNearCrashEvent(accidentEventDTO);
      }
    } catch (Exception e) {
      log.error("Could not handle AccidentEvent-Message. Message ignored.", e);
    }
    if (consumer != null) {
      consumer.ack();
    }
  }

  private void handleCrashEvent(AccidentEventDTO accidentEventDTO) throws ServiceException {
    log.info("Handling CrashEvent of {}", accidentEventDTO);

    List<CrashEventNotification> crashEventNotifications =
        crashEventNotificationService.generateFrom(accidentEventDTO);
    for (CrashEventNotification crashEventNotification : crashEventNotifications) {
      crashEventNotificationService.update(crashEventNotification);
    }

    List<SpotlightNotification> spotlightNotifications =
        spotlightNotificationService.generateFrom(accidentEventDTO);
    for (SpotlightNotification spotlightNotification : spotlightNotifications) {
      spotlightNotificationService.update(spotlightNotification);
    }

    List<SpeedNotification> speedNotifications =
        speedNotificationService.generateFrom(accidentEventDTO);
    for (SpeedNotification speedNotification : speedNotifications) {
      speedNotificationService.update(speedNotification);
    }
  }

  private void handleNearCrashEvent(AccidentEventDTO accidentEventDTO) throws ServiceException {
    log.info("Handling NearCrashEvent of {}", accidentEventDTO);
    List<NearCrashEventNotification> nearCrashEventNotifications =
        nearCrashEventNotificationService.generateFrom(accidentEventDTO);
    for (NearCrashEventNotification nearCrashEventNotification : nearCrashEventNotifications) {
      nearCrashEventNotificationService.update(nearCrashEventNotification);
    }
  }
}
