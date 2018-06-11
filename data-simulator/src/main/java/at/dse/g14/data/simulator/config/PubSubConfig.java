package at.dse.g14.data.simulator.config;

import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

@Configuration
@Profile("!test")
public class PubSubConfig {

  public static final String CHANNEL_EVENT_CLEARANCE = "clearanceEventInputChannel";
  public static final String CHANNEL_EVENT_ARRIVAL = "arrivalEventInputChannel";
  public static final String CHANNEL_TRACK = "vehicleTrackInputChannel";

  @Bean
  @ServiceActivator(inputChannel = CHANNEL_EVENT_CLEARANCE)
  public MessageHandler clearanceEventMessageSender(PubSubOperations pubsubTemplate) {
    return new PubSubMessageHandler(pubsubTemplate, "clearance-events");
  }

  @Bean
  @ServiceActivator(inputChannel = CHANNEL_EVENT_ARRIVAL)
  public MessageHandler arrivalEventMessageSender(PubSubOperations pubsubTemplate) {
    return new PubSubMessageHandler(pubsubTemplate, "arrival-events");
  }

  @Bean
  @ServiceActivator(inputChannel = CHANNEL_TRACK)
  public MessageHandler trackMessageSender(PubSubOperations pubsubTemplate) {
    return new PubSubMessageHandler(pubsubTemplate, "vehicle-tracks");
  }

  @MessagingGateway(defaultRequestChannel = CHANNEL_EVENT_CLEARANCE)
  public interface ClearanceEventOutboundGateway {

    void sendToPubsub(String text);
  }

  @MessagingGateway(defaultRequestChannel = CHANNEL_EVENT_ARRIVAL)
  public interface ArrivalEventOutboundGateway {

    void sendToPubsub(String text);
  }

  @MessagingGateway(defaultRequestChannel = CHANNEL_TRACK)
  public interface VehicleTrackOutboundGateway {
    void sendToPubsub(String text);
  }
}
