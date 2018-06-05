package at.dse.g14.persistence;

import at.dse.g14.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NotificationRepositoryTest {

  @Autowired private SpeedNotificationRepository speedNotificationRepository;

  @Autowired private NotificationRepository notificationRepository;

  @Autowired private ArrivalNotificationRepository arrivalNotificationRepository;

  @Autowired private ClearanceNotificationRepository clearanceNotificationRepository;

  @Autowired private SpotlightNotificationRepository spotlightNotificationRepository;

  @Autowired private CrashEventNotificationRepository crashEventNotificationRepository;

  @Autowired private NearCrashEventNotificationRepository nearCrashEventNotificationRepository;

  @Test
  public void test() throws Exception {
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
            .speed(new BigDecimal(130))
            .distanceVehicleAhead(new BigDecimal(20))
            .distanceVehicleBehind(new BigDecimal(15))
            .build();
    NearCrashEventNotification nearCrashEventNotification =
        NearCrashEventNotification.builder()
            .receiver("8971234981324")
            .vin("W0L000051T2123456")
            .modelType("Opel")
            .passengers(4)
            .location(new Double[] {20.0, 20.0})
            .speed(new BigDecimal(130))
            .distanceVehicleAhead(new BigDecimal(20))
            .distanceVehicleBehind(new BigDecimal(15))
            .build();
    crashEventNotificationRepository.save(crashEventNotification);
    speedNotificationRepository.save(speedNotification);
    arrivalNotificationRepository.save(arrivalNotification);
    clearanceNotificationRepository.save(clearanceNotification);
    spotlightNotificationRepository.save(spotlightNotification);
    nearCrashEventNotificationRepository.save(nearCrashEventNotification);
    for (Notification notification : notificationRepository.findAll()) {
      System.out.println(notification);
    }
  }
}
