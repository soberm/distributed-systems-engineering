package at.dse.g14;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PublisherExample {

  public void publishMessagesToTopic(String projectId, String topicId, List<String> messages) {

    ProjectTopicName topicName = ProjectTopicName.of(projectId, topicId);
    Publisher publisher = null;
    List<ApiFuture<String>> messageIdFutures = new ArrayList<>();

      try {
          // Create a publisher instance with default settings bound to the topic
          publisher = Publisher.newBuilder(topicName).build();

          // schedule publishing one message at a time : messages get automatically batched
          for (String message : messages) {
              ByteString data = ByteString.copyFromUtf8(message);
              PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

              // Once published, returns a server-assigned message id (unique within the topic)
              ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
              messageIdFutures.add(messageIdFuture);
          }
      } catch (IOException e) {
          e.printStackTrace();
      } finally {
          // wait on any pending publish requests.
          List<String> messageIds = null;
          try {
              messageIds = ApiFutures.allAsList(messageIdFutures).get();


          for (String messageId : messageIds) {
              System.out.println("published with message ID: " + messageId);
          }

          if (publisher != null) {
              // When finished with the publisher, shutdown to free up resources.
                  publisher.shutdown();
          }
      } catch (Exception e) {
              e.printStackTrace();
          }
      }
  }
}
