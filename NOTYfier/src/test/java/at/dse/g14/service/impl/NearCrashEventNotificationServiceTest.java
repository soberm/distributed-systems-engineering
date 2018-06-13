package at.dse.g14.service.impl;

import at.dse.g14.entity.Entity;
import at.dse.g14.entity.NearCrashEventNotification;
import at.dse.g14.service.AbstractCrudService;
import at.dse.g14.service.AbstractCrudServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Unit-Tests for the NearCrashEventNotificationService.
 *
 * @author Michael Sober
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class NearCrashEventNotificationServiceTest extends AbstractCrudServiceTest {

  @Autowired private NearCrashEventNotificationService nearCrashEventNotificationService;

  @Test
  public void save_alreadyExistingNearCrashEventNotification_shouldThrowException()
      throws Exception {
    save_alreadyExistingEntity_shouldThrowException();
  }

  @Test
  public void save_validNearCrashEventNotification_shouldPersist() throws Exception {
    save_validEntity_shouldPersist();
  }

  @Test
  public void update_validNearCrashEventNotification_shouldPersist() throws Exception {
    update_validEntity_shouldPersist();
  }

  @Test
  public void delete_validNearCrashEventNotification_shouldPersist() throws Exception {
    delete_validEntity_shouldPersist();
  }

  @Test
  public void findOne_populatedDatabase_shouldReturnNearCrashEventNotification() throws Exception {
    findOne_populatedDatabase_shouldReturnEntity();
  }

  @Test
  public void findOne_emptyDatabase_shouldReturnNull() throws Exception {
    super.findOne_emptyDatabase_shouldReturnNull();
  }

  @Test
  public void findAll_populatedDatabase_shouldReturnNearCrashEventNotifications() throws Exception {
    findAll_populatedDatabase_shouldReturnEntities();
  }

  @Test
  public void findAll_emptyDatabase_shouldReturnEmptyList() throws Exception {
    super.findAll_emptyDatabase_shouldReturnEmptyList();
  }

  @Override
  protected AbstractCrudService getService() {
    return nearCrashEventNotificationService;
  }

  @Override
  protected Entity updateValidEntity(Entity entity) {
    ((NearCrashEventNotification) entity).setReceiver("B8091234");
    return entity;
  }

  @Override
  public Entity buildValidEntity() {
    BigDecimal speed = BigDecimal.valueOf(130.00).setScale(2, RoundingMode.HALF_UP);
    BigDecimal distanceVehicleAhead = BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_UP);
    BigDecimal distanceVehicleBehind = BigDecimal.valueOf(15.00).setScale(2, RoundingMode.HALF_UP);
    return NearCrashEventNotification.builder()
        .receiver("WA8091234")
        .vin("W0L000051T2123456")
        .modelType("Opel")
        .passengers(4)
        .location(new Double[] {20.0, 20.0})
        .speed(speed)
        .distanceVehicleAhead(distanceVehicleAhead)
        .distanceVehicleBehind(distanceVehicleBehind)
        .build();
  }
}
