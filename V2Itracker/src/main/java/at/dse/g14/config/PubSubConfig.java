package at.dse.g14.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@Profile("!test")
public class PubSubConfig {

  @Bean
  public PubSubInboundChannelAdapter messageChannelAdapter(
      @Qualifier("vehicleTrackInputChannel") MessageChannel inputChannel,
      PubSubOperations pubSubTemplate) {
    PubSubInboundChannelAdapter adapter =
        new PubSubInboundChannelAdapter(pubSubTemplate, "v2i-tracker");
    adapter.setOutputChannel(inputChannel);
    adapter.setAckMode(AckMode.MANUAL);
    return adapter;
  }

  @Bean
  public MessageChannel vehicleTrackInputChannel() {
    return new DirectChannel();
  }

  @Bean
  @ServiceActivator(inputChannel = "accidentEventOutputChannel")
  public MessageHandler messageSender(PubSubOperations pubsubTemplate) {
    return new PubSubMessageHandler(pubsubTemplate, "accident-events");
  }

  @MessagingGateway(defaultRequestChannel = "accidentEventOutputChannel")
  public interface AccidentEventOutboundGateway {
    void sendToPubsub(String text);
  }

}
