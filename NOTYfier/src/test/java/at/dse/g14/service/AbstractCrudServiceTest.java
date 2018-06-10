package at.dse.g14.service;

import at.dse.g14.entity.Entity;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class AbstractCrudServiceTest<T extends Entity<ID>, ID> {

  protected abstract AbstractCrudService<T, ID> getService();

  protected void save_validEntity_shouldPersist() throws Exception {
    T entity = buildValidEntity();
    assertThat(getService().save(entity), is(entity));
    getService().delete(entity.getId()); // cleanup
  }

  protected void save_alreadyExistingEntity_shouldThrowException() throws Exception {
    T entity = buildValidEntity();
    T saved = getService().save(entity);
    try {
      getService().save(saved);
      Assert.fail("Exception should be thrown.");
    } catch (Exception e) {
      getService().delete(saved.getId()); // cleanup
    }
  }

  protected void update_validEntity_shouldPersist() throws Exception {
    T entity = buildValidEntity();
    T save = getService().save(entity);
    assertThat(save, is(entity));
    save = updateValidEntity(save);
    getService().update(save);
    assertThat(getService().findOne(save.getId()), is(save));
    getService().delete(save.getId()); // cleanup
  }

  protected abstract T updateValidEntity(T entity);

  protected void delete_validEntity_shouldPersist() throws Exception {
    T entity = buildValidEntity();
    assertThat(getService().save(entity), is(entity));
    getService().delete(entity.getId());
    assertThat(getService().findOne(entity.getId()), is(nullValue()));
  }

  protected void findOne_populatedDatabase_shouldReturnEntity() throws Exception {
    T entity1 = buildValidEntity();
    assertThat(getService().save(entity1), is(entity1));
    T entity2 = buildValidEntity();
    assertThat(getService().save(entity2), is(entity2));
    assertThat(getService().findOne(entity1.getId()), is(entity1));
    assertThat(getService().findOne(entity2.getId()), is(entity2));
    getService().delete(entity1.getId()); // cleanup
    getService().delete(entity2.getId()); // cleanup
  }

  protected void findOne_emptyDatabase_shouldReturnNull() throws Exception {
    assertThat(getService().findOne(null), is(nullValue()));
  }

  protected void findAll_populatedDatabase_shouldReturnEntities() throws Exception {
    T entity1 = buildValidEntity();
    assertThat(getService().save(entity1), is(entity1));
    T entity2 = buildValidEntity();
    assertThat(getService().save(entity2), is(entity2));
    List<T> vehicleTracks = Arrays.asList(entity1, entity2);
    assertThat(getService().findAll(), is(vehicleTracks));
    getService().delete(entity1.getId()); // cleanup
    getService().delete(entity2.getId()); // cleanup
  }

  protected void findAll_emptyDatabase_shouldReturnEmptyList() throws Exception {
    assertThat(getService().findAll(), IsEmptyCollection.empty());
  }

  protected abstract T buildValidEntity();
}
