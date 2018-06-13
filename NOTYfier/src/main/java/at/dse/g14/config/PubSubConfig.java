package at.dse.g14.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

/**
 * Google Pub/Sub configuration of the application.
 *
 * @author Michael Sober
 * @since 1.0
 */
@Configuration
@Profile("!test")
public class PubSubConfig {

  @Bean
  public PubSubInboundChannelAdapter messageChannelAdapter(
      @Qualifier("accidentEventInputChannel") MessageChannel inputChannel,
      PubSubOperations pubSubTemplate) {
    PubSubInboundChannelAdapter adapter =
        new PubSubInboundChannelAdapter(pubSubTemplate, "notyfier");
    adapter.setOutputChannel(inputChannel);
    adapter.setAckMode(AckMode.MANUAL);
    return adapter;
  }

  @Bean
  public PubSubInboundChannelAdapter messageChannelAdapterClearance(
      @Qualifier("clearanceEventInputChannel") MessageChannel inputChannel,
      PubSubOperations pubSubTemplate) {
    PubSubInboundChannelAdapter adapter =
        new PubSubInboundChannelAdapter(pubSubTemplate, "notyfier-clearance");
    adapter.setOutputChannel(inputChannel);
    adapter.setAckMode(AckMode.MANUAL);
    return adapter;
  }

  @Bean
  public PubSubInboundChannelAdapter messageChannelAdapterArrival(
      @Qualifier("arrivalEventInputChannel") MessageChannel inputChannel,
      PubSubOperations pubSubTemplate) {
    PubSubInboundChannelAdapter adapter =
        new PubSubInboundChannelAdapter(pubSubTemplate, "notyfier-arrival");
    adapter.setOutputChannel(inputChannel);
    adapter.setAckMode(AckMode.MANUAL);
    return adapter;
  }

  @Bean
  public MessageChannel accidentEventInputChannel() {
    return new DirectChannel();
  }

  @Bean
  public MessageChannel clearanceEventInputChannel() {
    return new DirectChannel();
  }

  @Bean
  public MessageChannel arrivalEventInputChannel() {
    return new DirectChannel();
  }
}
