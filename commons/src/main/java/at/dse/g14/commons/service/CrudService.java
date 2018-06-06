package at.dse.g14.commons.service;

import at.dse.g14.commons.service.exception.ServiceException;
import java.util.List;

public interface CrudService<T, ID> {

  T save(T entity) throws ServiceException;

  T update(T entity) throws ServiceException;

  void delete(ID id) throws ServiceException;

  T findOne(ID id) throws ServiceException;

  List<T> findAll() throws ServiceException;

}
