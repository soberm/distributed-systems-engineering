package at.dse.g14;

// Imports the Google Cloud client library

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.SubscriptionAdminClient;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.pubsub.v1.*;

public class PubSubExamples {

  // Your Google Cloud Platform project ID
  private String projectId;

  private String topicId;

  private String pullSubscriptionId;
  private String pushSubscriptionId;

  public PubSubExamples(
      String projectId, String topicId, String pullSubscriptionId, String pushSubscriptionId) {
    this.projectId = projectId;
    this.topicId = topicId;
    this.pullSubscriptionId = pullSubscriptionId;
    this.pushSubscriptionId = pushSubscriptionId;
  }

  public static void main(String[] args) {
    PubSubExamples examples =
        new PubSubExamples(
            "dse-group-14",
            "exampleTopic1",
            "examplePullSubscription1",
            "examplePushSubscription1");


    examples.createTopic(examples.projectId, examples.topicId);
    examples.listTopics(examples.projectId);

    examples.createPullSubscription(
        examples.projectId, examples.topicId, examples.pullSubscriptionId);
    // Kann nicht funktioneiren, endpoint gibts nicht, nur als beispiel
    //    examples.createPushSubscription(examples.projectId, examples.topicId,
    // examples.pushSubscriptionId, "https://example.endpint.com");
    //    examples.deleteSubscription(examples.projectId, examples.pushSubscriptionId);
    examples.listSubscriptionsOfProject(examples.projectId);
    examples.listSubscriptionsOfTopic(examples.topicId);
    examples.deleteSubscription(examples.projectId, examples.pullSubscriptionId);

    examples.deleteTopic(examples.projectId, examples.topicId);
    examples.listTopics(examples.projectId);
  }

  /** ************************ TOPICS ************************ */
  private void createTopic(String projectId, String topicId) {
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

  private void deleteTopic(String projectId, String topicId) {
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
          ListTopicsRequest.newBuilder().setProject(ProjectName.format(projectId)).build();
      TopicAdminClient.ListTopicsPagedResponse response =
          topicAdminClient.listTopics(listTopicsRequest);
      Iterable<Topic> topics = response.iterateAll();
      System.out.println("Topic List: ");
      for (Topic topic : topics) {
        System.out.printf("Topic %s:is available.\n", topic.getName());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** ************************ SUBSCRIPTIONS ************************ */
  private Subscription createPullSubscription(
      String projectId, String topicId, String subscriptionId) {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      // eg. projectId = "my-test-project", topicId = "my-test-topic"
      ProjectTopicName topicName = ProjectTopicName.of(projectId, topicId);
      // eg. pullSubscriptionId = "my-test-subscription"
      ProjectSubscriptionName subscriptionName =
          ProjectSubscriptionName.of(projectId, subscriptionId);
      // create a pull subscription with default acknowledgement deadline
      Subscription subscription =
          subscriptionAdminClient.createSubscription(
              subscriptionName, topicName, PushConfig.getDefaultInstance(), 0);
      System.out.println("Pull-Subscription " + subscription.getName() + "created.");
      return subscription;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private Subscription createPushSubscription(
      String projectId, String topicId, String subscriptionId, String endpoint) {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ProjectTopicName topicName = ProjectTopicName.of(projectId, topicId);
      ProjectSubscriptionName subscriptionName =
          ProjectSubscriptionName.of(projectId, subscriptionId);

      // eg. endpoint = "https://my-test-project.appspot.com/push"
      PushConfig pushConfig = PushConfig.newBuilder().setPushEndpoint(endpoint).build();

      // acknowledgement deadline in seconds for the message received over the push endpoint
      int ackDeadlineInSeconds = 10;

      Subscription subscription =
          subscriptionAdminClient.createSubscription(
              subscriptionName, topicName, pushConfig, ackDeadlineInSeconds);
      System.out.println("Push-Subscription " + subscription.getName() + " created.");
      return subscription;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private void deleteSubscription(String projectId, String subscriptionId) {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ProjectSubscriptionName subscriptionName =
          ProjectSubscriptionName.of(projectId, subscriptionId);
      subscriptionAdminClient.deleteSubscription(subscriptionName);
      System.out.println(
          "Subscription "
              + subscriptionName.getProject()
              + ":"
              + subscriptionName.getSubscription()
              + " deleted.");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void listSubscriptionsOfProject(String projectId) {
    try (SubscriptionAdminClient subscriptionAdminClient = SubscriptionAdminClient.create()) {
      ListSubscriptionsRequest listSubscriptionsRequest =
          ListSubscriptionsRequest.newBuilder()
              .setProject(ProjectName.of(projectId).toString())
              .build();
      SubscriptionAdminClient.ListSubscriptionsPagedResponse response =
          subscriptionAdminClient.listSubscriptions(listSubscriptionsRequest);
      Iterable<Subscription> subscriptions = response.iterateAll();
      System.out.println("List Subscriptions of project " + projectId + ":");
      for (Subscription subscription : subscriptions) {
        System.out.println("Subscription " + subscription.getName() + " available.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void listSubscriptionsOfTopic(String topicId) {
    try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
      TopicName topicName = ProjectTopicName.of(projectId, topicId);
      ListTopicSubscriptionsRequest request =
          ListTopicSubscriptionsRequest.newBuilder().setTopic(topicName.toString()).build();
      TopicAdminClient.ListTopicSubscriptionsPagedResponse response =
          topicAdminClient.listTopicSubscriptions(request);
      Iterable<String> subscriptionNames = response.iterateAll();
      System.out.println("List Subscriptions of topic " + topicId + ":");
      for (String subscriptionName : subscriptionNames) {
        System.out.println("Subscription " + subscriptionName + " available.");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
