package at.dse.g14.persistence;

import at.dse.g14.entity.AccidentStatistics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccidentStatisticsRepository extends CrudRepository<AccidentStatistics, Long> {

}
