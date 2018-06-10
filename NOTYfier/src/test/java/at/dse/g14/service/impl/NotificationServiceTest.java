package at.dse.g14.service.impl;

import at.dse.g14.entity.*;
import at.dse.g14.persistence.*;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NotificationServiceTest {

  @Autowired private NotificationService notificationService;

  @Autowired private SpeedNotificationRepository speedNotificationRepository;

  @Autowired private NotificationRepository notificationRepository;

  @Autowired private ArrivalNotificationRepository arrivalNotificationRepository;

  @Autowired private ClearanceNotificationRepository clearanceNotificationRepository;

  @Autowired private SpotlightNotificationRepository spotlightNotificationRepository;

  @Autowired private CrashEventNotificationRepository crashEventNotificationRepository;

  @Autowired private NearCrashEventNotificationRepository nearCrashEventNotificationRepository;

  @Test
  public void findAll_populatedDatabase_shouldReturnNotifications() throws Exception {
    BigDecimal speed = BigDecimal.valueOf(130.00).setScale(2, RoundingMode.HALF_UP);
    BigDecimal distanceVehicleAhead = BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_UP);
    BigDecimal distanceVehicleBehind = BigDecimal.valueOf(15.00).setScale(2, RoundingMode.HALF_UP);
    SpeedNotification speedNotification = new SpeedNotification(null, "1234");
    ArrivalNotification arrivalNotification = new ArrivalNotification(null, "12341234");
    ClearanceNotification clearanceNotification = new ClearanceNotification(null, "512345");
    SpotlightNotification spotlightNotification = new SpotlightNotification(null, "123451234");
    CrashEventNotification crashEventNotification =
        CrashEventNotification.builder()
            .receiver("12349789701234")
            .vin("W0L000051T2123456")
            .modelType("Opel")
            .passengers(4)
            .location(new Double[] {20.0, 20.0})
            .speed(speed)
            .distanceVehicleAhead(distanceVehicleAhead)
            .distanceVehicleBehind(distanceVehicleBehind)
            .build();
    NearCrashEventNotification nearCrashEventNotification =
        NearCrashEventNotification.builder()
            .receiver("8971234981324")
            .vin("W0L000051T2123456")
            .modelType("Opel")
            .passengers(4)
            .location(new Double[] {20.0, 20.0})
            .speed(speed)
            .distanceVehicleAhead(distanceVehicleAhead)
            .distanceVehicleBehind(distanceVehicleBehind)
            .build();
    List<Notification> notifications = new ArrayList<>();
    speedNotificationRepository.save(speedNotification);
    notifications.add(speedNotification);
    arrivalNotificationRepository.save(arrivalNotification);
    notifications.add(arrivalNotification);
    clearanceNotificationRepository.save(clearanceNotification);
    notifications.add(clearanceNotification);
    spotlightNotificationRepository.save(spotlightNotification);
    notifications.add(spotlightNotification);
    crashEventNotificationRepository.save(crashEventNotification);
    notifications.add(crashEventNotification);
    nearCrashEventNotificationRepository.save(nearCrashEventNotification);
    notifications.add(nearCrashEventNotification);

    assertThat(notificationService.findAll(), is(notifications));

    // Cleanup
    speedNotificationRepository.deleteById(speedNotification.getId());
    arrivalNotificationRepository.deleteById(arrivalNotification.getId());
    clearanceNotificationRepository.deleteById(clearanceNotification.getId());
    spotlightNotificationRepository.deleteById(spotlightNotification.getId());
    crashEventNotificationRepository.deleteById(crashEventNotification.getId());
    nearCrashEventNotificationRepository.deleteById(nearCrashEventNotification.getId());
  }

  @Test
  public void findByReceiver_populatedDatabase_shouldReturnNotificationsForReceiver()
      throws Exception {

    String receiver1 = "ABC1234";
    String receiver2 = "DEF1234";

    BigDecimal speed = BigDecimal.valueOf(130.00).setScale(2, RoundingMode.HALF_UP);
    BigDecimal distanceVehicleAhead = BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_UP);
    BigDecimal distanceVehicleBehind = BigDecimal.valueOf(15.00).setScale(2, RoundingMode.HALF_UP);
    SpeedNotification speedNotification = new SpeedNotification(null, receiver1);
    ArrivalNotification arrivalNotification = new ArrivalNotification(null, receiver1);
    ClearanceNotification clearanceNotification = new ClearanceNotification(null, receiver1);
    SpotlightNotification spotlightNotification = new SpotlightNotification(null, receiver2);
    CrashEventNotification crashEventNotification =
        CrashEventNotification.builder()
            .receiver(receiver2)
            .vin("W0L000051T2123456")
            .modelType("Opel")
            .passengers(4)
            .location(new Double[] {20.0, 20.0})
            .speed(speed)
            .distanceVehicleAhead(distanceVehicleAhead)
            .distanceVehicleBehind(distanceVehicleBehind)
            .build();
    NearCrashEventNotification nearCrashEventNotification =
        NearCrashEventNotification.builder()
            .receiver(receiver2)
            .vin("W0L000051T2123456")
            .modelType("Opel")
            .passengers(4)
            .location(new Double[] {20.0, 20.0})
            .speed(speed)
            .distanceVehicleAhead(distanceVehicleAhead)
            .distanceVehicleBehind(distanceVehicleBehind)
            .build();

    List<Notification> notificationsReceiver1 = new ArrayList<>();
    List<Notification> notificationsReceiver2 = new ArrayList<>();

    speedNotificationRepository.save(speedNotification);
    notificationsReceiver1.add(speedNotification);
    arrivalNotificationRepository.save(arrivalNotification);
    notificationsReceiver1.add(arrivalNotification);
    clearanceNotificationRepository.save(clearanceNotification);
    notificationsReceiver1.add(clearanceNotification);
    spotlightNotificationRepository.save(spotlightNotification);
    notificationsReceiver2.add(spotlightNotification);
    crashEventNotificationRepository.save(crashEventNotification);
    notificationsReceiver2.add(crashEventNotification);
    nearCrashEventNotificationRepository.save(nearCrashEventNotification);
    notificationsReceiver2.add(nearCrashEventNotification);

    assertThat(notificationService.findByReceiver(receiver1), is(notificationsReceiver1));
    assertThat(notificationService.findByReceiver(receiver2), is(notificationsReceiver2));

    // Cleanup
    speedNotificationRepository.deleteById(speedNotification.getId());
    arrivalNotificationRepository.deleteById(arrivalNotification.getId());
    clearanceNotificationRepository.deleteById(clearanceNotification.getId());
    spotlightNotificationRepository.deleteById(spotlightNotification.getId());
    crashEventNotificationRepository.deleteById(crashEventNotification.getId());
    nearCrashEventNotificationRepository.deleteById(nearCrashEventNotification.getId());
  }

  @Test
  public void findAll_emptyDatabase_shouldReturnEmptyList() throws Exception {
    assertThat(notificationService.findAll(), IsEmptyCollection.empty());
  }
}
