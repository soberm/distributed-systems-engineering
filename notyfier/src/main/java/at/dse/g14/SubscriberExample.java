package at.dse.g14;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

public class SubscriberExample {

  //    public void subscribe(Subscription subscription) {
  public void subscribe(String projectId, String subscriptionId) {
    ProjectSubscriptionName subscriptionName =
        ProjectSubscriptionName.of(projectId, subscriptionId);
    // Instantiate an asynchronous message receiver
    MessageReceiver receiver =
        new MessageReceiver() {
          @Override
          public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
            // handle incoming message, then ack/nack the received message
            System.out.println("Received Message with Id : " + message.getMessageId());
            System.out.println("Message Data : " + message.getData().toStringUtf8());
            consumer.ack();
          }
        };

    Subscriber subscriber = null;
    // Create a subscriber for "my-subscription-id" bound to the message receiver
    subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
    subscriber.startAsync();
    // ...
  }

  public void stopListening(Subscriber subscriber) {
    if (subscriber != null) {
      subscriber.stopAsync();
    }
  }
}
