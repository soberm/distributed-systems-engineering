package at.dse.g14;

// Imports the Google Cloud client library

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.ListTopicsRequest;
import com.google.pubsub.v1.ProjectName;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.Topic;

public class PubSubExamples {

  // Your Google Cloud Platform project ID
  private String projectId;

  private String topicId;

  public PubSubExamples(String projectId, String topicId) {
    this.projectId = projectId;
    this.topicId = topicId;
  }

  public static void main(String[] args) {
    PubSubExamples examples = new PubSubExamples("dse-group-14", "exampleTopic1");
    examples.createTopic();
    examples.listTopics(examples.projectId);
    examples.deleteTopic();
    examples.listTopics(examples.projectId);
  }

  private void createTopic() {
    /*
     * Create a new topic
     */
    ProjectTopicName topic = ProjectTopicName.of(projectId, topicId);
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      topicAdminClient.createTopic(topic);
    } catch (ApiException e) {
      // example : code = ALREADY_EXISTS(409) implies topic already exists
      System.out.print(e.getStatusCode().getCode());
      System.out.print(e.isRetryable());
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.printf("Topic %s:%s created.\n", topic.getProject(), topic.getTopic());
  }

  private void deleteTopic() {
    /*
     * Delete a new topic
     */
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      ProjectTopicName topicName = ProjectTopicName.of(projectId, topicId);
      topicAdminClient.deleteTopic(topicName);
      System.out.printf("Topic %s:%s deleted.\n", topicName.getProject(), topicName.getTopic());
    } catch (ApiException e) {
      System.out.print(e.getStatusCode().getCode());
      System.out.print(e.isRetryable());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void listTopics(String topicName) {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
        ListTopicsRequest listTopicsRequest =
                ListTopicsRequest.newBuilder()
                        .setProject(ProjectName.format(projectId))
                        .build();
        TopicAdminClient.ListTopicsPagedResponse response = topicAdminClient.listTopics(listTopicsRequest);
      Iterable<Topic> topics = response.iterateAll();
      System.out.println("Topic List: ");
      for (Topic topic : topics) {
        System.out.printf("Topic %s:is available.\n", topic.getName());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
