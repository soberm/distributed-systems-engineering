package at.dse.g14.persistence;

import at.dse.g14.entity.AccidentStatistics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to manage AccidentStatistics.
 *
 * @author Michael Sober
 * @since 1.0
 * @see CrudRepository
 */
@Repository
public interface AccidentStatisticsRepository extends CrudRepository<AccidentStatistics, Long> {}
