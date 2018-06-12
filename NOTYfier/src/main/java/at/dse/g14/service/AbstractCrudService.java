package at.dse.g14.service;

import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.Entity;
import at.dse.g14.service.exception.EntityAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;

@Slf4j
public abstract class AbstractCrudService<T extends Entity<ID>, ID> implements CrudService<T, ID> {

  protected final CrudRepository<T, ID> crudRepository;
  protected final Validator validator;

  public AbstractCrudService(CrudRepository<T, ID> crudRepository, Validator validator) {
    this.crudRepository = crudRepository;
    this.validator = validator;
  }

  @Override
  public T save(T entity) throws ServiceException {
    validate(entity);

    if (findOne(entity.getId()) != null) {
      throw new EntityAlreadyExistsException(entity + " already exists.");
    }

    log.info("Saving " + entity);
    return crudRepository.save(entity);
  }

  @Override
  public T update(T entity) throws ServiceException {
    validate(entity);

    T loadedEntity = findOne(entity.getId());

    if (loadedEntity == null) {
      log.info(entity + " does not exist. Creating a new one.");
      return save(entity);
    }

    loadedEntity = updateLoadedEntity(loadedEntity, entity);

    log.info("Updating " + loadedEntity);
    return crudRepository.save(loadedEntity);
  }

  protected abstract T updateLoadedEntity(T loadedEntity, T entity);

  @Override
  public void delete(ID id) throws ServiceException {
    log.info("Deleted entity " + id);
    crudRepository.deleteById(id);
  }

  @Override
  public T findOne(ID id) throws ServiceException {
    log.info("Finding entity " + id);
    if (id == null) {
      return null;
    }

    try {
      return crudRepository.findById(id).get();
    } catch (NoSuchElementException | IllegalArgumentException e) {
      return null;
    }
  }

  @Override
  public List<T> findAll() throws ServiceException {
    log.info("Finding all entities.");
    return (List<T>) crudRepository.findAll();
  }

  protected void validate(T entity) throws ValidationException {
    log.debug("Validating " + entity);
    if (!validator.validate(entity).isEmpty()) {
      throw new ValidationException(entity + " not valid.");
    }
  }
}
