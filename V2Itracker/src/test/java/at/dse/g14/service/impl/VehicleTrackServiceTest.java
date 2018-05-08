package at.dse.g14.service.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import at.dse.g14.entity.GpsPoint;
import at.dse.g14.entity.VehicleTrack;
import at.dse.g14.service.IVehicleTrackService;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VehicleTrackServiceTest {

  @Autowired
  private IVehicleTrackService vehicleTrackService;

  @Test
  public void save_validVehicleTrack_shouldPersist(){
    VehicleTrack vehicleTrack = buildValidVehicleTrack(0L);
    assertThat(vehicleTrackService.save(vehicleTrack), is(vehicleTrack));
  }

  @Test
  public void update_validVehicleTrack_shouldPersist(){
    Long id = 0L;
    VehicleTrack vehicleTrack = buildValidVehicleTrack(id);
    VehicleTrack savedVehicleTrack = vehicleTrackService.save(vehicleTrack);
    assertThat(savedVehicleTrack, is(vehicleTrack));
    savedVehicleTrack.setCrashEvent(true);
    vehicleTrackService.update(savedVehicleTrack);
    assertThat(vehicleTrackService.findOne(id), is(savedVehicleTrack));
    vehicleTrackService.delete(id); //cleanup
  }

  @Test
  public void delete_validVehicleTrack_shouldPersist(){
    Long id = 0L;
    VehicleTrack vehicleTrack = buildValidVehicleTrack(id);
    assertThat(vehicleTrackService.save(vehicleTrack), is(vehicleTrack));
    vehicleTrackService.delete(id);
    assertThat(vehicleTrackService.findOne(id), is(nullValue()));
  }

  @Test
  public void findOne_emptyDatabase_shouldReturnNull(){
    assertThat(vehicleTrackService.findOne(0L), is(nullValue()));
  }

  @Test
  public void findAll_emptyDatabase_shouldReturnEmptyList() {
    assertThat(vehicleTrackService.findAll(), IsEmptyCollection.empty());
  }

  public VehicleTrack buildValidVehicleTrack(Long id){
    return VehicleTrack.builder()
        .id(id)
        .vin("W0L000051T2123456")
        .modelType("Opel")
        .passengers(4)
        .location(new GpsPoint(20, 20))
        .speed(130)
        .distanceVehicleAhead(20)
        .distanceVehicleBehind(15)
        .nearCrashEvent(false)
        .crashEvent(false)
        .build();
  }

}
