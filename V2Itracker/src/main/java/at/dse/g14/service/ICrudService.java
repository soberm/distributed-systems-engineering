package at.dse.g14.service;

import at.dse.g14.service.exception.ServiceException;
import java.util.List;

public interface ICrudService<T, ID> {

  T save(T entity) throws ServiceException;

  T update(T entity) throws ServiceException;

  void delete(ID id) throws ServiceException;

  T findOne(ID id) throws ServiceException;

  List<T> findAll() throws ServiceException;

}
