package at.dse.g14.commons.service;

import at.dse.g14.commons.service.exception.ServiceException;

import java.util.List;

/**
 * This interface defines the necessary functionality of an CrudService.
 *
 * @author Michael Sober
 * @since 1.0
 */
public interface CrudService<T, ID> {

  /**
   * Saves an entity.
   *
   * @param entity which should be saved.
   * @return the saved entity.
   * @throws ServiceException if an error saving the entity occurs.
   */
  T save(T entity) throws ServiceException;

  /**
   * Updates an entity.
   *
   * @param entity which should be updated.
   * @return the updated entity.
   * @throws ServiceException if an error updating the entity occurs.
   */
  T update(T entity) throws ServiceException;

  /**
   * Deletes an entity by providing the id.
   *
   * @param id of the entity which should be deleted.
   * @throws ServiceException if an error deleting the entity occurs.
   */
  void delete(ID id) throws ServiceException;

  /**
   * Finds the entity with the given id.
   *
   * @param id of the entity which should be found.
   * @return the found entity.
   * @throws ServiceException if an error finding the entity occurs.
   */
  T findOne(ID id) throws ServiceException;

  /**
   * FInding all entities.
   *
   * @return all found entities.
   * @throws ServiceException if an error finding all entities occur.
   */
  List<T> findAll() throws ServiceException;
}
