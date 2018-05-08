package at.dse.g14.service.impl;

import at.dse.g14.entity.VehicleTrack;
import at.dse.g14.persistence.VehicleTrackRepository;
import at.dse.g14.service.IVehicleTrackService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleTrackService implements IVehicleTrackService {

  @Autowired
  private VehicleTrackRepository vehicleTrackRepository;

  @Override
  public VehicleTrack save(VehicleTrack vehicleTrack) {
    return vehicleTrackRepository.save(vehicleTrack);
  }

  @Override
  public void update(VehicleTrack vehicleTrack) {
    vehicleTrackRepository.save(vehicleTrack);
  }

  @Override
  public void delete(Long id) {
    vehicleTrackRepository.delete(id);
  }

  @Override
  public VehicleTrack findOne(Long id) {
    return vehicleTrackRepository.findOne(id);
  }

  @Override
  public List<VehicleTrack> findAll() {
    return vehicleTrackRepository.findAll();
  }

}
