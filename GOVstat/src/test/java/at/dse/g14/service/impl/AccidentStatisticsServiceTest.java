package at.dse.g14.service.impl;

import at.dse.g14.commons.dto.GpsPoint;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.entity.AccidentStatistics;
import at.dse.g14.service.IAccidentStatisticsService;
import at.dse.g14.service.exception.AccidentStatisticsAlreadyExistsException;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit-Tests for the AccidentStatisticsService.
 *
 * @author Michael Sober
 * @since 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AccidentStatisticsServiceTest {

  @Autowired private IAccidentStatisticsService accidentStatisticsService;

  @Test
  public void save_validAccidentStatistics_shouldPersist() throws Exception {
    AccidentStatistics accidentStatistics = buildValidAccidentStatistics();
    assertThat(accidentStatisticsService.save(accidentStatistics), is(accidentStatistics));
    accidentStatisticsService.delete(accidentStatistics.getId()); // cleanup
  }

  @Test
  public void save_alreadyExistingAccidentStatistics_shouldThrowException() throws Exception {
    AccidentStatistics accidentStatistics = buildValidAccidentStatistics();
    accidentStatisticsService.save(accidentStatistics);
    try {
      accidentStatisticsService.save(accidentStatistics);
      Assert.fail("AccidentStatisticsAlreadyExistsException should be thrown.");
    } catch (AccidentStatisticsAlreadyExistsException e) {
      accidentStatisticsService.delete(accidentStatistics.getId()); // cleanup
    }
  }

  @Test(expected = ValidationException.class)
  public void save_invalidAccidentStatisticsVin_shouldThrowException() throws Exception {
    AccidentStatistics accidentStatistics = buildValidAccidentStatistics();
    accidentStatistics.setVin(null);
    accidentStatisticsService.save(accidentStatistics);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidAccidentStatisticsModelType_shouldThrowException() throws Exception {
    AccidentStatistics accidentStatistics = buildValidAccidentStatistics();
    accidentStatistics.setModelType(null);
    accidentStatisticsService.save(accidentStatistics);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidAccidentStatisticsPassengers_shouldThrowException() throws Exception {
    AccidentStatistics accidentStatistics = buildValidAccidentStatistics();
    accidentStatistics.setPassengers(301);
    accidentStatisticsService.save(accidentStatistics);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidAccidentStatisticsArrivalTimeEmergencyService_shouldThrowException()
      throws Exception {
    AccidentStatistics accidentStatistics = buildValidAccidentStatistics();
    accidentStatistics.setArrivalTimeEmergencyService(-1);
    accidentStatisticsService.save(accidentStatistics);
  }

  @Test(expected = ValidationException.class)
  public void save_invalidAccidentStatisticsClearanceTimeAccidentSpot_shouldThrowException()
      throws Exception {
    AccidentStatistics accidentStatistics = buildValidAccidentStatistics();
    accidentStatistics.setClearanceTimeAccidentSpot(-1);
    accidentStatisticsService.save(accidentStatistics);
  }

  @Test
  public void update_validAccidentStatistics_shouldPersist() throws Exception {
    AccidentStatistics accidentStatistics = buildValidAccidentStatistics();
    AccidentStatistics savedAccidentStatistics = accidentStatisticsService.save(accidentStatistics);
    assertThat(savedAccidentStatistics, is(accidentStatistics));
    System.out.println(savedAccidentStatistics);
    System.out.println(accidentStatisticsService.findOne(savedAccidentStatistics.getId()));
    savedAccidentStatistics.setPassengers(5);
    accidentStatisticsService.update(savedAccidentStatistics);
    assertThat(
        accidentStatisticsService.findOne(savedAccidentStatistics.getId()),
        is(savedAccidentStatistics));
    accidentStatisticsService.delete(savedAccidentStatistics.getId()); // cleanup
  }

  @Test
  public void update_notExistingAccidentStatistics_shouldPersist() throws Exception {
    AccidentStatistics accidentStatistics = buildValidAccidentStatistics();
    assertThat(accidentStatisticsService.update(accidentStatistics), is(accidentStatistics));
    accidentStatisticsService.delete(accidentStatistics.getId()); // cleanup
  }

  @Test(expected = ValidationException.class)
  public void update_invalidAccidentStatistics_shouldThrowException() throws Exception {
    AccidentStatistics accidentStatistics = buildValidAccidentStatistics();
    AccidentStatistics savedAccidentStatistics = accidentStatisticsService.save(accidentStatistics);
    assertThat(savedAccidentStatistics, is(accidentStatistics));
    savedAccidentStatistics.setArrivalTimeEmergencyService(-1);
    accidentStatisticsService.update(savedAccidentStatistics);
  }

  @Test
  public void delete_validAccidentStatistics_shouldPersist() throws Exception {
    AccidentStatistics accidentStatistics = buildValidAccidentStatistics();
    assertThat(accidentStatisticsService.save(accidentStatistics), is(accidentStatistics));
    accidentStatisticsService.delete(accidentStatistics.getId());
    assertThat(accidentStatisticsService.findOne(accidentStatistics.getId()), is(nullValue()));
  }

  @Test
  public void findOne_populatedDatabase_shouldReturnAccidentStatistics() throws Exception {
    AccidentStatistics accidentStatistics1 = buildValidAccidentStatistics();
    assertThat(accidentStatisticsService.save(accidentStatistics1), is(accidentStatistics1));
    AccidentStatistics accidentStatistics2 = buildValidAccidentStatistics();
    assertThat(accidentStatisticsService.save(accidentStatistics2), is(accidentStatistics2));
    assertThat(
        accidentStatisticsService.findOne(accidentStatistics1.getId()), is(accidentStatistics1));
    assertThat(
        accidentStatisticsService.findOne(accidentStatistics2.getId()), is(accidentStatistics2));
    accidentStatisticsService.delete(accidentStatistics1.getId()); // cleanup
    accidentStatisticsService.delete(accidentStatistics2.getId()); // cleanup
  }

  @Test
  public void findOne_emptyDatabase_shouldReturnNull() throws Exception {
    assertThat(accidentStatisticsService.findOne(0L), is(nullValue()));
  }

  @Test
  public void findAll_populatedDatabase_shouldReturnAccidentStatistics() throws Exception {
    AccidentStatistics accidentStatistics1 = buildValidAccidentStatistics();
    assertThat(accidentStatisticsService.save(accidentStatistics1), is(accidentStatistics1));
    AccidentStatistics accidentStatistics2 = buildValidAccidentStatistics();
    assertThat(accidentStatisticsService.save(accidentStatistics2), is(accidentStatistics2));
    List<AccidentStatistics> accidentStatistics =
        Arrays.asList(accidentStatistics1, accidentStatistics2);
    assertThat(accidentStatisticsService.findAll(), is(accidentStatistics));
    accidentStatisticsService.delete(accidentStatistics1.getId()); // cleanup
    accidentStatisticsService.delete(accidentStatistics2.getId()); // cleanup
  }

  @Test
  public void findAll_emptyDatabase_shouldReturnEmptyList() throws Exception {
    assertThat(accidentStatisticsService.findAll(), IsEmptyCollection.empty());
  }

  public AccidentStatistics buildValidAccidentStatistics() {
    BigDecimal lat = BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_UP);
    BigDecimal lon = BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_UP);
    return AccidentStatistics.builder()
        .vin("W0L000051T2123456")
        .modelType("Opel")
        .passengers(4)
        .location(new Double[] {20.0, 20.0})
        .arrivalTimeEmergencyService(5)
        .clearanceTimeAccidentSpot(30)
        .build();
  }
}
