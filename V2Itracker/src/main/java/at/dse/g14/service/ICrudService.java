package at.dse.g14.service;

import java.util.List;

public interface ICrudService<T, ID> {

  T save(T entity);

  void update(T entity);

  void delete(ID s);

  T findOne(ID id);

  List<T> findAll();

}
