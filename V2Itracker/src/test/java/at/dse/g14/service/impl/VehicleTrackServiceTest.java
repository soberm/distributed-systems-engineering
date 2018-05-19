package at.dse.g14.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import at.dse.g14.entity.GpsPoint;
import at.dse.g14.entity.VehicleTrack;
import at.dse.g14.service.IVehicleTrackService;
import at.dse.g14.service.exception.ValidationException;
import at.dse.g14.service.exception.VehicleTrackAlreadyExistsException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Assert;
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
  public void save_validVehicleTrack_shouldPersist() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(0L);
    assertThat(vehicleTrackService.save(vehicleTrack), is(vehicleTrack));
    vehicleTrackService.delete(vehicleTrack.getId()); //cleanup
  }

  @Test
  public void save_alreadyExistingVehicleTrack_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(0L);
    vehicleTrackService.save(vehicleTrack);
    try {
      vehicleTrackService.save(vehicleTrack);
      Assert.fail("VehicleTrackAlreadyExistsException should be thrown.");
    } catch (VehicleTrackAlreadyExistsException e) {
      vehicleTrackService.delete(vehicleTrack.getId()); //cleanup
    }
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackVin_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(0L);
    vehicleTrack.setVin(null);
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackModelType_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(0L);
    vehicleTrack.setModelType(null);
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackPassengers_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(0L);
    vehicleTrack.setPassengers(301);
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackLocationLat_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(0L);
    vehicleTrack.setLocation(new GpsPoint(new BigDecimal(-91), new BigDecimal(180)));
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackLocationLon_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(0L);
    vehicleTrack.setLocation(new GpsPoint(new BigDecimal(-90), new BigDecimal(181)));
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackSpeed_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(0L);
    vehicleTrack.setSpeed(new BigDecimal(-1));
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackDistanceVehicleBehind_shouldThrowException()
      throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(0L);
    vehicleTrack.setDistanceVehicleBehind(new BigDecimal(-1));
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackDistanceVehicleAhead_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(0L);
    vehicleTrack.setDistanceVehicleAhead(new BigDecimal(-1));
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackNearCrashEvent_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(0L);
    vehicleTrack.setNearCrashEvent(null);
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackCrashEvent_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(0L);
    vehicleTrack.setCrashEvent(null);
    vehicleTrackService.save(vehicleTrack);
  }

  @Test
  public void update_validVehicleTrack_shouldPersist() throws Exception {
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
  public void update_notExistingVehicleTrack_shouldPersist() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(0L);
    assertThat(vehicleTrackService.update(vehicleTrack), is(vehicleTrack));
    vehicleTrackService.delete(vehicleTrack.getId()); //cleanup
  }

  @Test(expected = ValidationException.class)
  public void update_invalidVehicleTrack_shouldThrowException() throws Exception {
    Long id = 0L;
    VehicleTrack vehicleTrack = buildValidVehicleTrack(id);
    VehicleTrack savedVehicleTrack = vehicleTrackService.save(vehicleTrack);
    assertThat(savedVehicleTrack, is(vehicleTrack));
    savedVehicleTrack.setCrashEvent(null);
    vehicleTrackService.update(savedVehicleTrack);
  }

  @Test
  public void delete_validVehicleTrack_shouldPersist() throws Exception {
    Long id = 0L;
    VehicleTrack vehicleTrack = buildValidVehicleTrack(id);
    assertThat(vehicleTrackService.save(vehicleTrack), is(vehicleTrack));
    vehicleTrackService.delete(id);
    assertThat(vehicleTrackService.findOne(id), is(nullValue()));
  }

  @Test(expected = ValidationException.class)
  public void delete_invalidVehicleTrack_shouldThrowException() throws Exception {
    vehicleTrackService.delete(-1L);
  }

  @Test
  public void findOne_populatedDatabase_shouldReturnVehicleTrack() throws Exception {
    Long id1 = 0L;
    VehicleTrack vehicleTrack1 = buildValidVehicleTrack(id1);
    assertThat(vehicleTrackService.save(vehicleTrack1), is(vehicleTrack1));
    Long id2 = 1L;
    VehicleTrack vehicleTrack2 = buildValidVehicleTrack(id2);
    assertThat(vehicleTrackService.save(vehicleTrack2), is(vehicleTrack2));
    assertThat(vehicleTrackService.findOne(id1), is(vehicleTrack1));
    assertThat(vehicleTrackService.findOne(id2), is(vehicleTrack2));
    vehicleTrackService.delete(id1); //cleanup
    vehicleTrackService.delete(id2); //cleanup
  }

  @Test
  public void findOne_emptyDatabase_shouldReturnNull() throws Exception {
    assertThat(vehicleTrackService.findOne(0L), is(nullValue()));
  }

  @Test(expected = ValidationException.class)
  public void findOne_invalidVehicleTrack_shouldThrowException() throws Exception {
    vehicleTrackService.findOne(-1L);
  }

  @Test
  public void findAll_populatedDatabase_shouldReturnVehicleTracks() throws Exception {
    Long id1 = 0L;
    VehicleTrack vehicleTrack1 = buildValidVehicleTrack(id1);
    assertThat(vehicleTrackService.save(vehicleTrack1), is(vehicleTrack1));
    Long id2 = 1L;
    VehicleTrack vehicleTrack2 = buildValidVehicleTrack(id2);
    assertThat(vehicleTrackService.save(vehicleTrack2), is(vehicleTrack2));
    List<VehicleTrack> vehicleTracks = Arrays.asList(vehicleTrack1, vehicleTrack2);
    assertThat(vehicleTrackService.findAll(), is(vehicleTracks));
    vehicleTrackService.delete(id1); //cleanup
    vehicleTrackService.delete(id2); //cleanup
  }

  @Test
  public void findAll_emptyDatabase_shouldReturnEmptyList() throws Exception {
    assertThat(vehicleTrackService.findAll(), IsEmptyCollection.empty());
  }

  public VehicleTrack buildValidVehicleTrack(Long id) {
    return VehicleTrack.builder()
        .id(id)
        .vin("W0L000051T2123456")
        .modelType("Opel")
        .passengers(4)
        .location(new GpsPoint(new BigDecimal(20), new BigDecimal(20)))
        .speed(new BigDecimal(130))
        .distanceVehicleAhead(new BigDecimal(20))
        .distanceVehicleBehind(new BigDecimal(15))
        .nearCrashEvent(false)
        .crashEvent(false)
        .build();
  }

}
