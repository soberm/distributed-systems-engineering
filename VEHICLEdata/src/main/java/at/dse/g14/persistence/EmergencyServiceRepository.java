package at.dse.g14.persistence;

import at.dse.g14.entity.EmergencyServiceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Repository
public interface EmergencyServiceRepository extends CrudRepository<EmergencyServiceEntity, String> {

}
