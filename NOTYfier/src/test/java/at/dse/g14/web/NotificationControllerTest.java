package at.dse.g14.web;

import at.dse.g14.entity.*;
import at.dse.g14.service.impl.NotificationService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit-Tests for the NotificationController.
 *
 * @author Michael Sober
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class NotificationControllerTest {

  private String receiver1;
  private String receiver2;

  private List<Notification> notifications;
  private List<Notification> notificationsReceiver1;
  private List<Notification> notificationsReceiver2;

  @Autowired private MockMvc mockMvc;

  @Autowired private Gson gson;

  @MockBean @Autowired private NotificationService notificationService;

  @Before
  public void setup() throws Exception {

    receiver1 = "ABC1234";
    receiver2 = "DEF1234";

    SpeedNotification speedNotification = new SpeedNotification(null, receiver1);
    ArrivalNotification arrivalNotification = new ArrivalNotification(null, receiver1);
    ClearanceNotification clearanceNotification = new ClearanceNotification(null, receiver1);
    SpotlightNotification spotlightNotification = new SpotlightNotification(null, receiver2);

    notifications =
        Arrays.asList(
            speedNotification, arrivalNotification, clearanceNotification, spotlightNotification);
    notificationsReceiver1 = Arrays.asList(speedNotification, arrivalNotification);
    notificationsReceiver2 = Arrays.asList(clearanceNotification, spotlightNotification);

    Mockito.when(notificationService.findAll()).thenReturn(notifications);
    Mockito.when(notificationService.findByReceiver(receiver1)).thenReturn(notificationsReceiver1);
    Mockito.when(notificationService.findByReceiver(receiver2)).thenReturn(notificationsReceiver2);
  }

  @Test
  public void getNotifications_WithoutReceiver_ShouldReturnAllNotifications() throws Exception {
    mockMvc
        .perform(
            get("/notification")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(notifications)))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  public void getNotifications_WithReceiver_1_ShouldReturnNotificationsForReceiver()
      throws Exception {
    mockMvc
        .perform(
            get("/notification")
                .param("receiver", receiver1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(notificationsReceiver1)))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  public void getNotifications_WithReceiver_2_ShouldReturnNotificationsForReceiver()
      throws Exception {
    mockMvc
        .perform(
            get("/notification")
                .param("receiver", receiver2)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(notificationsReceiver2)))
        .andExpect(status().isOk())
        .andReturn();
  }
}
