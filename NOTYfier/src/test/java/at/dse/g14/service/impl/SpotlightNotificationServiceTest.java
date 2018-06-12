package at.dse.g14.service.impl;

import at.dse.g14.entity.Entity;
import at.dse.g14.entity.SpotlightNotification;
import at.dse.g14.service.AbstractCrudService;
import at.dse.g14.service.AbstractCrudServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit-Tests for the SpotlightNotificationService.
 *
 * @author Michael Sober
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpotlightNotificationServiceTest extends AbstractCrudServiceTest {

  @Autowired private SpotlightNotificationService spotlightNotificationService;

  @Test
  public void save_alreadyExistingSpotlightNotification_shouldThrowException() throws Exception {
    save_alreadyExistingEntity_shouldThrowException();
  }

  @Test
  public void save_validSpotlightNotification_shouldPersist() throws Exception {
    save_validEntity_shouldPersist();
  }

  @Test
  public void update_validSpotlightNotification_shouldPersist() throws Exception {
    update_validEntity_shouldPersist();
  }

  @Test
  public void delete_validSpotlightNotification_shouldPersist() throws Exception {
    delete_validEntity_shouldPersist();
  }

  @Test
  public void findOne_populatedDatabase_shouldReturnSpotlightNotification() throws Exception {
    findOne_populatedDatabase_shouldReturnEntity();
  }

  @Test
  public void findOne_emptyDatabase_shouldReturnNull() throws Exception {
    super.findOne_emptyDatabase_shouldReturnNull();
  }

  @Test
  public void findAll_populatedDatabase_shouldReturnSpotlightNotifications() throws Exception {
    findAll_populatedDatabase_shouldReturnEntities();
  }

  @Test
  public void findAll_emptyDatabase_shouldReturnEmptyList() throws Exception {
    super.findAll_emptyDatabase_shouldReturnEmptyList();
  }

  @Override
  protected AbstractCrudService getService() {
    return spotlightNotificationService;
  }

  @Override
  protected Entity updateValidEntity(Entity entity) {
    ((SpotlightNotification) entity).setReceiver("B8091234");
    return entity;
  }

  @Override
  public Entity buildValidEntity() {
    return new SpotlightNotification(null, "ABC8091234");
  }
}
