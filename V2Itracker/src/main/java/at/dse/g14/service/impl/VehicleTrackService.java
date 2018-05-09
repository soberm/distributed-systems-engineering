package at.dse.g14.service.impl;

import at.dse.g14.entity.VehicleTrack;
import at.dse.g14.persistence.VehicleTrackRepository;
import at.dse.g14.service.IVehicleTrackService;
import at.dse.g14.service.exception.ServiceException;
import at.dse.g14.service.exception.ValidationException;
import java.util.List;
import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VehicleTrackService implements IVehicleTrackService {

  @Autowired
  private Validator validator;

  @Autowired
  private VehicleTrackRepository vehicleTrackRepository;

  @Override
  public VehicleTrack save(VehicleTrack vehicleTrack) throws ServiceException {
    validate(vehicleTrack);
    return vehicleTrackRepository.save(vehicleTrack);
  }

  @Override
  public void update(VehicleTrack vehicleTrack) throws ServiceException {
    validate(vehicleTrack);
    vehicleTrackRepository.save(vehicleTrack);
  }

  @Override
  public void delete(Long id) throws ServiceException {
    validate(id);
    vehicleTrackRepository.delete(id);
  }

  @Override
  public VehicleTrack findOne(Long id) throws ServiceException {
    validate(id);
    return vehicleTrackRepository.findOne(id);
  }

  @Override
  public List<VehicleTrack> findAll() throws ServiceException {
    return vehicleTrackRepository.findAll();
  }

  private void validate(VehicleTrack vehicleTrack) throws ValidationException {
    if (!validator.validate(vehicleTrack).isEmpty()) {
      throw new ValidationException("VehicleTrack not valid.");
    }
  }

  private void validate(Long id) throws ValidationException {
    if (id < 0) {
      throw new ValidationException("Id must be greater than 0.");
    }
  }

}
