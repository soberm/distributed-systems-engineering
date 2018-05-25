package at.dse.g14.persistence;

import at.dse.g14.entity.AccidentStatisticsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccidentStatisticsRepository extends CrudRepository<AccidentStatisticsEntity, Long> {

}
