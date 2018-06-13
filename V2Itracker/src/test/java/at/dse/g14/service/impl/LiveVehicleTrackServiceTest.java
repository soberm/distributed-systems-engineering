package at.dse.g14.service.impl;

import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.config.PubSubConfig.AccidentEventOutboundGateway;
import at.dse.g14.entity.LiveVehicleTrack;
import at.dse.g14.service.ILiveVehicleTrackService;
import at.dse.g14.service.exception.LiveVehicleTrackAlreadyExistsException;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit-Tests for the LiveVehicleTrackService.
 *
 * @author Michael Sober
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class LiveVehicleTrackServiceTest {

  @Autowired private ILiveVehicleTrackService liveVehicleTrackService;

  @MockBean private AccidentEventOutboundGateway accidentEventOutboundGateway;

  @Test
  public void save_validLiveVehicleTrack_shouldPersist() throws Exception {
    LiveVehicleTrack liveVehicleTrack = buildValidLiveVehicleTrack("W0L000051T2123456");
    assertThat(liveVehicleTrackService.save(liveVehicleTrack), is(liveVehicleTrack));
    liveVehicleTrackService.delete(liveVehicleTrack.getVin()); // cleanup
  }

  @Test
  public void save_alreadyExistingLiveVehicleTrack_shouldThrowException() throws Exception {
    LiveVehicleTrack liveVehicleTrack = buildValidLiveVehicleTrack("W0L000051T2123456");
    liveVehicleTrackService.save(liveVehicleTrack);
    try {
      liveVehicleTrackService.save(liveVehicleTrack);
      Assert.fail("LiveVehicleTrackAlreadyExistsException should be thrown.");
    } catch (LiveVehicleTrackAlreadyExistsException e) {
      liveVehicleTrackService.delete(liveVehicleTrack.getVin()); // cleanup
    }
  }

  @Test(expected = ValidationException.class)
  public void save_invalidLiveVehicleTrackModelType_shouldThrowException() throws Exception {
    LiveVehicleTrack liveVehicleTrack = buildValidLiveVehicleTrack("W0L000051T2123456");
    liveVehicleTrack.setModelType(null);
    liveVehicleTrackService.save(liveVehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidLiveVehicleTrackLocation_shouldThrowException() throws Exception {
    LiveVehicleTrack liveVehicleTrack = buildValidLiveVehicleTrack("W0L000051T2123456");
    liveVehicleTrack.setLocation(new Double[] {1.0});
    liveVehicleTrackService.save(liveVehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidLiveVehicleTrackSpeed_shouldThrowException() throws Exception {
    LiveVehicleTrack liveVehicleTrack = buildValidLiveVehicleTrack("W0L000051T2123456");
    liveVehicleTrack.setSpeed(new BigDecimal(-1));
    liveVehicleTrackService.save(liveVehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidLiveVehicleTrackDistanceVehicleBehind_shouldThrowException()
      throws Exception {
    LiveVehicleTrack liveVehicleTrack = buildValidLiveVehicleTrack("W0L000051T2123456");
    liveVehicleTrack.setDistanceVehicleBehind(new BigDecimal(-1));
    liveVehicleTrackService.save(liveVehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidLiveVehicleTrackDistanceVehicleAhead_shouldThrowException()
      throws Exception {
    LiveVehicleTrack liveVehicleTrack = buildValidLiveVehicleTrack("W0L000051T2123456");
    liveVehicleTrack.setDistanceVehicleAhead(new BigDecimal(-1));
    liveVehicleTrackService.save(liveVehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidLiveVehicleTrackNearCrashEvent_shouldThrowException() throws Exception {
    LiveVehicleTrack liveVehicleTrack = buildValidLiveVehicleTrack("W0L000051T2123456");
    liveVehicleTrack.setNearCrashEvent(null);
    liveVehicleTrackService.save(liveVehicleTrack);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidLiveVehicleTrackCrashEvent_shouldThrowException() throws Exception {
    LiveVehicleTrack liveVehicleTrack = buildValidLiveVehicleTrack("W0L000051T2123456");
    liveVehicleTrack.setCrashEvent(null);
    liveVehicleTrackService.save(liveVehicleTrack);
  }

  @Test
  public void update_validLiveVehicleTrack_shouldPersist() throws Exception {
    String vin = "W0L000051T2123456";
    LiveVehicleTrack liveVehicleTrack = buildValidLiveVehicleTrack(vin);
    LiveVehicleTrack savedLiveVehicleTrack = liveVehicleTrackService.save(liveVehicleTrack);
    assertThat(savedLiveVehicleTrack, is(liveVehicleTrack));
    savedLiveVehicleTrack.setCrashEvent(true);
    liveVehicleTrackService.update(savedLiveVehicleTrack);
    assertThat(liveVehicleTrackService.findOne(vin), is(savedLiveVehicleTrack));
    liveVehicleTrackService.delete(vin); // cleanup
  }

  @Test
  public void update_notExistingLiveVehicleTrack_shouldPersist() throws Exception {
    LiveVehicleTrack liveVehicleTrack = buildValidLiveVehicleTrack("W0L000051T2123456");
    assertThat(liveVehicleTrackService.update(liveVehicleTrack), is(liveVehicleTrack));
    liveVehicleTrackService.delete(liveVehicleTrack.getVin()); // cleanup
  }

  @Test(expected = ValidationException.class)
  public void update_invalidLiveVehicleTrack_shouldThrowException() throws Exception {
    String vin = "W0L000051T2123456";
    LiveVehicleTrack liveVehicleTrack = buildValidLiveVehicleTrack(vin);
    LiveVehicleTrack savedLiveVehicleTrack = liveVehicleTrackService.save(liveVehicleTrack);
    assertThat(savedLiveVehicleTrack, is(liveVehicleTrack));
    savedLiveVehicleTrack.setCrashEvent(null);
    liveVehicleTrackService.update(savedLiveVehicleTrack);
  }

  @Test
  public void delete_validLiveVehicleTrack_shouldPersist() throws Exception {
    String vin = "W0L000051T2123456";
    LiveVehicleTrack liveVehicleTrack = buildValidLiveVehicleTrack(vin);
    assertThat(liveVehicleTrackService.save(liveVehicleTrack), is(liveVehicleTrack));
    liveVehicleTrackService.delete(vin);
    assertThat(liveVehicleTrackService.findOne(vin), is(nullValue()));
  }

  @Test
  public void findOne_populatedDatabase_shouldReturnLiveVehicleTrack() throws Exception {
    String vin1 = "W0L000151T2123456";
    LiveVehicleTrack liveVehicleTrack1 = buildValidLiveVehicleTrack(vin1);
    assertThat(liveVehicleTrackService.save(liveVehicleTrack1), is(liveVehicleTrack1));
    String vin2 = "W0L000251T2123456";
    LiveVehicleTrack liveVehicleTrack2 = buildValidLiveVehicleTrack(vin2);
    assertThat(liveVehicleTrackService.save(liveVehicleTrack2), is(liveVehicleTrack2));
    assertThat(liveVehicleTrackService.findOne(vin1), is(liveVehicleTrack1));
    assertThat(liveVehicleTrackService.findOne(vin2), is(liveVehicleTrack2));
    liveVehicleTrackService.delete(vin1); // cleanup
    liveVehicleTrackService.delete(vin2); // cleanup
  }

  @Test
  public void findOne_emptyDatabase_shouldReturnNull() throws Exception {
    assertThat(liveVehicleTrackService.findOne("W0L000051T2123456"), is(nullValue()));
  }

  @Test
  public void findAll_populatedDatabase_shouldReturnLiveVehicleTracks() throws Exception {
    String vin1 = "W0L000151T2123456";
    LiveVehicleTrack liveVehicleTrack1 = buildValidLiveVehicleTrack(vin1);
    assertThat(liveVehicleTrackService.save(liveVehicleTrack1), is(liveVehicleTrack1));
    String vin2 = "W0L000251T2123456";
    LiveVehicleTrack liveVehicleTrack2 = buildValidLiveVehicleTrack(vin2);
    assertThat(liveVehicleTrackService.save(liveVehicleTrack2), is(liveVehicleTrack2));
    List<LiveVehicleTrack> vehicleTracks = Arrays.asList(liveVehicleTrack1, liveVehicleTrack2);
    assertThat(liveVehicleTrackService.findAll(), is(vehicleTracks));
    liveVehicleTrackService.delete(vin1); // cleanup
    liveVehicleTrackService.delete(vin2); // cleanup
  }

  @Test
  public void findAll_emptyDatabase_shouldReturnEmptyList() throws Exception {
    assertThat(liveVehicleTrackService.findAll(), IsEmptyCollection.empty());
  }

  @Test
  public void findByLocationWithin_populatedDatabase_shouldReturnLiveVehicleTracksInRange()
      throws Exception {
    String vin1 = "W0L002051T2123456";
    LiveVehicleTrack liveVehicleTrack1 = buildValidLiveVehicleTrack(vin1);
    liveVehicleTrack1.setLocation(new Double[] {48.208174, 16.373819}); // Vienna
    assertThat(liveVehicleTrackService.save(liveVehicleTrack1), is(liveVehicleTrack1));

    String vin2 = "W0L000151T2123456";
    LiveVehicleTrack liveVehicleTrack2 = buildValidLiveVehicleTrack(vin2);
    liveVehicleTrack2.setLocation(new Double[] {48.309667, 16.322878}); // Klosterneuburg
    assertThat(liveVehicleTrackService.save(liveVehicleTrack2), is(liveVehicleTrack2));

    String vin3 = "W0L003451T2123456";
    LiveVehicleTrack liveVehicleTrack3 = buildValidLiveVehicleTrack(vin3);
    liveVehicleTrack3.setLocation(new Double[] {48.198591, 16.363106}); // Vienna
    assertThat(liveVehicleTrackService.save(liveVehicleTrack3), is(liveVehicleTrack3));

    Double[] location = liveVehicleTrack1.getLocation();
    List<LiveVehicleTrack> vehicleTracksInRange =
        Arrays.asList(liveVehicleTrack1, liveVehicleTrack3);

    Point point = new Point(location[0], location[1]);
    Distance distance = new Distance(10, Metrics.KILOMETERS);
    assertThat(
        liveVehicleTrackService.findByLocationNear(point, distance), is(vehicleTracksInRange));

    liveVehicleTrackService.delete(vin1); // cleanup
    liveVehicleTrackService.delete(vin2); // cleanup
    liveVehicleTrackService.delete(vin3); // cleanup
  }

  public LiveVehicleTrack buildValidLiveVehicleTrack(String vin) {
    return LiveVehicleTrack.builder()
        .vin(vin)
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
