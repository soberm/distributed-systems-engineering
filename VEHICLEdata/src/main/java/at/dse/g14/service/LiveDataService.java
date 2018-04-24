package at.dse.g14.service;

import at.dse.g14.commons.dto.LiveData;
import at.dse.g14.service.exception.ValidationException;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
public interface LiveDataService {

  LiveData create(final LiveData data) throws ValidationException;
}
