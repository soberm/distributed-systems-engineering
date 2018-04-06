import at.dse.g14.PubSubHelpers;
import at.dse.g14.PublisherExample;
import at.dse.g14.SubscriberExample;
import com.google.pubsub.v1.Subscription;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExampleAsTestPubSub {
  // TODO: write publish subscribe test with pull strategy

  @Test
  public void exampleTestPubSub() {
    PubSubHelpers pubSubHelpers = new PubSubHelpers();
    String projectId = "dse-group-14";
    String topic = "testTopic";
    String subscriptionId = "testSubscription";
    pubSubHelpers.createTopic(projectId, topic);
        Subscription sub =
            pubSubHelpers.createPullSubscription(projectId, topic, subscriptionId);
    SubscriberExample subscriber = new SubscriberExample();
    subscriber.subscribe(projectId, subscriptionId);
    PublisherExample publisher = new PublisherExample();
    List<String> messages = new ArrayList<>();
    messages.add("message1");
    messages.add("message2");
    publisher.publishMessagesToTopic(projectId, topic, messages);

    pubSubHelpers.deleteSubscription(projectId, subscriptionId);
    pubSubHelpers.deleteTopic(projectId, topic);
  }
}
