package at.dse.g14.service.impl;

import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.VehicleTrack;
import at.dse.g14.service.ILiveVehicleTrackService;
import at.dse.g14.service.IVehicleTrackService;
import at.dse.g14.service.exception.VehicleTrackAlreadyExistsException;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit-Tests for the VehicleTrackService.
 *
 * @author Michael Sober
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class VehicleTrackServiceTest {

  @Autowired private IVehicleTrackService vehicleTrackService;

  @MockBean private ILiveVehicleTrackService liveVehicleTrackService;

  @Test
  public void save_validVehicleTrack_shouldPersist() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(UUID.randomUUID().toString());
    assertThat(vehicleTrackService.save(vehicleTrack), is(vehicleTrack));
    vehicleTrackService.delete(vehicleTrack.getId()); // cleanup
  }

  @Test
  public void save_alreadyExistingVehicleTrack_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(UUID.randomUUID().toString());
    vehicleTrackService.save(vehicleTrack);
    try {
      vehicleTrackService.save(vehicleTrack);
      Assert.fail("VehicleTrackAlreadyExistsException should be thrown.");
    } catch (VehicleTrackAlreadyExistsException e) {
      vehicleTrackService.delete(vehicleTrack.getId()); // cleanup
    }
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackVin_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(UUID.randomUUID().toString());
    vehicleTrack.setVin(null);
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackModelType_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(UUID.randomUUID().toString());
    vehicleTrack.setModelType(null);
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackPassengers_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(UUID.randomUUID().toString());
    vehicleTrack.setPassengers(301);
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackLocation_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(UUID.randomUUID().toString());
    vehicleTrack.setLocation(new Double[] {1.0});
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackSpeed_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(UUID.randomUUID().toString());
    vehicleTrack.setSpeed(new BigDecimal(-1));
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackDistanceVehicleBehind_shouldThrowException()
      throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(UUID.randomUUID().toString());
    vehicleTrack.setDistanceVehicleBehind(new BigDecimal(-1));
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackDistanceVehicleAhead_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(UUID.randomUUID().toString());
    vehicleTrack.setDistanceVehicleAhead(new BigDecimal(-1));
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackNearCrashEvent_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(UUID.randomUUID().toString());
    vehicleTrack.setNearCrashEvent(null);
    vehicleTrackService.save(vehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidVehicleTrackCrashEvent_shouldThrowException() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(UUID.randomUUID().toString());
    vehicleTrack.setCrashEvent(null);
    vehicleTrackService.save(vehicleTrack);
  }

  @Test
  public void update_validVehicleTrack_shouldPersist() throws Exception {
    String id = UUID.randomUUID().toString();
    VehicleTrack vehicleTrack = buildValidVehicleTrack(id);
    VehicleTrack savedVehicleTrack = vehicleTrackService.save(vehicleTrack);
    assertThat(savedVehicleTrack, is(vehicleTrack));
    savedVehicleTrack.setCrashEvent(true);
    vehicleTrackService.update(savedVehicleTrack);
    assertThat(vehicleTrackService.findOne(id), is(savedVehicleTrack));
    vehicleTrackService.delete(id); // cleanup
  }

  @Test
  public void update_notExistingVehicleTrack_shouldPersist() throws Exception {
    VehicleTrack vehicleTrack = buildValidVehicleTrack(UUID.randomUUID().toString());
    assertThat(vehicleTrackService.update(vehicleTrack), is(vehicleTrack));
    vehicleTrackService.delete(vehicleTrack.getId()); // cleanup
  }

  @Test(expected = ValidationException.class)
  public void update_invalidVehicleTrack_shouldThrowException() throws Exception {
    String id = UUID.randomUUID().toString();
    VehicleTrack vehicleTrack = buildValidVehicleTrack(id);
    VehicleTrack savedVehicleTrack = vehicleTrackService.save(vehicleTrack);
    assertThat(savedVehicleTrack, is(vehicleTrack));
    savedVehicleTrack.setCrashEvent(null);
    vehicleTrackService.update(savedVehicleTrack);
  }

  @Test
  public void delete_validVehicleTrack_shouldPersist() throws Exception {
    String id = UUID.randomUUID().toString();
    VehicleTrack vehicleTrack = buildValidVehicleTrack(id);
    assertThat(vehicleTrackService.save(vehicleTrack), is(vehicleTrack));
    vehicleTrackService.delete(id);
    assertThat(vehicleTrackService.findOne(id), is(nullValue()));
  }

  @Test
  public void findOne_populatedDatabase_shouldReturnVehicleTrack() throws Exception {
    String id1 = UUID.randomUUID().toString();
    VehicleTrack vehicleTrack1 = buildValidVehicleTrack(id1);
    assertThat(vehicleTrackService.save(vehicleTrack1), is(vehicleTrack1));
    String id2 = UUID.randomUUID().toString();
    VehicleTrack vehicleTrack2 = buildValidVehicleTrack(id2);
    assertThat(vehicleTrackService.save(vehicleTrack2), is(vehicleTrack2));
    assertThat(vehicleTrackService.findOne(id1), is(vehicleTrack1));
    assertThat(vehicleTrackService.findOne(id2), is(vehicleTrack2));
    vehicleTrackService.delete(id1); // cleanup
    vehicleTrackService.delete(id2); // cleanup
  }

  @Test
  public void findOne_emptyDatabase_shouldReturnNull() throws Exception {
    assertThat(vehicleTrackService.findOne(UUID.randomUUID().toString()), is(nullValue()));
  }

  @Test
  public void findAll_populatedDatabase_shouldReturnVehicleTracks() throws Exception {
    String id1 = UUID.randomUUID().toString();
    VehicleTrack vehicleTrack1 = buildValidVehicleTrack(id1);
    assertThat(vehicleTrackService.save(vehicleTrack1), is(vehicleTrack1));
    String id2 = UUID.randomUUID().toString();
    VehicleTrack vehicleTrack2 = buildValidVehicleTrack(id2);
    assertThat(vehicleTrackService.save(vehicleTrack2), is(vehicleTrack2));
    List<VehicleTrack> vehicleTracks = Arrays.asList(vehicleTrack1, vehicleTrack2);
    assertThat(vehicleTrackService.findAll(), is(vehicleTracks));
    vehicleTrackService.delete(id1); // cleanup
    vehicleTrackService.delete(id2); // cleanup
  }

  @Test
  public void findAll_emptyDatabase_shouldReturnEmptyList() throws Exception {
    assertThat(vehicleTrackService.findAll(), IsEmptyCollection.empty());
  }

  public VehicleTrack buildValidVehicleTrack(String id) {
    return VehicleTrack.builder()
        .id(id)
        .vin("W0L000051T2123456")
        .modelType("Opel")
        .passengers(4)
        .location(new Double[] {20.0, 20.0})
        .speed(new BigDecimal(130))
        .distanceVehicleAhead(new BigDecimal(20))
        .distanceVehicleBehind(new BigDecimal(15))
        .nearCrashEvent(false)
        .crashEvent(false)
        .build();
  }
}
