package at.dse.g14;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.Subscription;

public class SubscriberExample {

    private String projectId;

    public SubscriberExample(String projectId, String subscriptionId) {
        this.projectId = projectId;
    }

    public void subscribe(Subscription subscription) {

        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscription.getName());
        // Instantiate an asynchronous message receiver
        MessageReceiver receiver =
                new MessageReceiver() {
                    @Override
                    public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
                        // handle incoming message, then ack/nack the received message
                        System.out.println("Id : " + message.getMessageId());
                        System.out.println("Data : " + message.getData().toStringUtf8());
                        consumer.ack();
                    }
                };

        Subscriber subscriber = null;
        try {
            // Create a subscriber for "my-subscription-id" bound to the message receiver
            subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
            subscriber.startAsync();
            // ...
        } finally {
            // stop receiving messages
            if (subscriber != null) {
                subscriber.stopAsync();
            }
        }
    }
}
