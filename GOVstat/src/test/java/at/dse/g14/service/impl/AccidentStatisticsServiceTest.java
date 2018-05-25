package at.dse.g14.service.impl;


import at.dse.g14.commons.dto.AccidentStatistics;
import at.dse.g14.commons.dto.GpsPoint;
import at.dse.g14.commons.service.exception.ValidationException;
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

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccidentStatisticsServiceTest {

    @Autowired
    private IAccidentStatisticsService accidentStatisticsService;

    @Test
    public void save_validAccidentStatistics_shouldPersist() throws Exception {
        AccidentStatistics accidentStatistics = buildValidAccidentStatistics(0L);
        assertThat(accidentStatisticsService.save(accidentStatistics), is(accidentStatistics));
        accidentStatisticsService.delete(accidentStatistics.getId()); //cleanup
    }

    @Test
    public void save_alreadyExistingAccidentStatistics_shouldThrowException() throws Exception {
        AccidentStatistics accidentStatistics = buildValidAccidentStatistics(0L);
        accidentStatisticsService.save(accidentStatistics);
        try {
            accidentStatisticsService.save(accidentStatistics);
            Assert.fail("AccidentStatisticsAlreadyExistsException should be thrown.");
        } catch (AccidentStatisticsAlreadyExistsException e) {
            accidentStatisticsService.delete(accidentStatistics.getId()); //cleanup
        }
    }

    @Test(expected = ValidationException.class)
    public void save_invalidAccidentStatisticsVin_shouldThrowException() throws Exception {
        AccidentStatistics accidentStatistics = buildValidAccidentStatistics(0L);
        accidentStatistics.setVin(null);
        accidentStatisticsService.save(accidentStatistics);
    }

    @Test(expected = ValidationException.class)
    public void save_invalidAccidentStatisticsModelType_shouldThrowException() throws Exception {
        AccidentStatistics accidentStatistics = buildValidAccidentStatistics(0L);
        accidentStatistics.setModelType(null);
        accidentStatisticsService.save(accidentStatistics);
    }

    @Test(expected = ValidationException.class)
    public void save_invalidAccidentStatisticsPassengers_shouldThrowException() throws Exception {
        AccidentStatistics accidentStatistics = buildValidAccidentStatistics(0L);
        accidentStatistics.setPassengers(301);
        accidentStatisticsService.save(accidentStatistics);
    }

    @Test(expected = ValidationException.class)
    public void save_invalidAccidentStatisticsLocationLat_shouldThrowException() throws Exception {
        AccidentStatistics accidentStatistics = buildValidAccidentStatistics(0L);
        accidentStatistics.setLocation(new GpsPoint(new BigDecimal(-91), new BigDecimal(180)));
        accidentStatisticsService.save(accidentStatistics);
    }

    @Test(expected = ValidationException.class)
    public void save_invalidAccidentStatisticsLocationLon_shouldThrowException() throws Exception {
        AccidentStatistics accidentStatistics = buildValidAccidentStatistics(0L);
        accidentStatistics.setLocation(new GpsPoint(new BigDecimal(-90), new BigDecimal(181)));
        accidentStatisticsService.save(accidentStatistics);
    }

    @Test(expected = ValidationException.class)
    public void save_invalidAccidentStatisticsArrivalTimeEmergencyService_shouldThrowException() throws Exception {
        AccidentStatistics accidentStatistics = buildValidAccidentStatistics(0L);
        accidentStatistics.setArrivalTimeEmergencyService(-1);
        accidentStatisticsService.save(accidentStatistics);
    }

    @Test(expected = ValidationException.class)
    public void save_invalidAccidentStatisticsClearanceTimeAccidentSpot_shouldThrowException() throws Exception {
        AccidentStatistics accidentStatistics = buildValidAccidentStatistics(0L);
        accidentStatistics.setClearanceTimeAccidentSpot(-1);
        accidentStatisticsService.save(accidentStatistics);
    }

    @Test
    public void update_validAccidentStatistics_shouldPersist() throws Exception {
        Long id = 0L;
        AccidentStatistics accidentStatistics = buildValidAccidentStatistics(id);
        AccidentStatistics savedAccidentStatistics = accidentStatisticsService.save(accidentStatistics);
        assertThat(savedAccidentStatistics, is(accidentStatistics));
        System.out.println(savedAccidentStatistics);
        System.out.println(accidentStatisticsService.findOne(id));
        savedAccidentStatistics.setPassengers(5);
        accidentStatisticsService.update(savedAccidentStatistics);
        assertThat(accidentStatisticsService.findOne(id), is(savedAccidentStatistics));
        accidentStatisticsService.delete(id); //cleanup
    }

    @Test
    public void update_notExistingAccidentStatistics_shouldPersist() throws Exception {
        AccidentStatistics accidentStatistics = buildValidAccidentStatistics(0L);
        assertThat(accidentStatisticsService.update(accidentStatistics), is(accidentStatistics));
        accidentStatisticsService.delete(accidentStatistics.getId()); //cleanup
    }

    @Test(expected = ValidationException.class)
    public void update_invalidAccidentStatistics_shouldThrowException() throws Exception {
        Long id = 0L;
        AccidentStatistics accidentStatistics = buildValidAccidentStatistics(id);
        AccidentStatistics savedAccidentStatistics = accidentStatisticsService.save(accidentStatistics);
        assertThat(savedAccidentStatistics, is(accidentStatistics));
        savedAccidentStatistics.setArrivalTimeEmergencyService(-1);
        accidentStatisticsService.update(savedAccidentStatistics);
    }

    @Test
    public void delete_validAccidentStatistics_shouldPersist() throws Exception {
        Long id = 0L;
        AccidentStatistics accidentStatistics = buildValidAccidentStatistics(id);
        assertThat(accidentStatisticsService.save(accidentStatistics), is(accidentStatistics));
        accidentStatisticsService.delete(id);
        assertThat(accidentStatisticsService.findOne(id), is(nullValue()));
    }

    @Test(expected = ValidationException.class)
    public void delete_invalidAccidentStatistics_shouldThrowException() throws Exception {
        accidentStatisticsService.delete(-1L);
    }

    @Test
    public void findOne_populatedDatabase_shouldReturnAccidentStatistics() throws Exception {
        Long id1 = 0L;
        AccidentStatistics accidentStatistics1 = buildValidAccidentStatistics(id1);
        assertThat(accidentStatisticsService.save(accidentStatistics1), is(accidentStatistics1));
        Long id2 = 1L;
        AccidentStatistics accidentStatistics2 = buildValidAccidentStatistics(id2);
        assertThat(accidentStatisticsService.save(accidentStatistics2), is(accidentStatistics2));
        assertThat(accidentStatisticsService.findOne(id1), is(accidentStatistics1));
        assertThat(accidentStatisticsService.findOne(id2), is(accidentStatistics2));
        accidentStatisticsService.delete(id1); //cleanup
        accidentStatisticsService.delete(id2); //cleanup
    }

    @Test
    public void findOne_emptyDatabase_shouldReturnNull() throws Exception {
        assertThat(accidentStatisticsService.findOne(0L), is(nullValue()));
    }

    @Test(expected = ValidationException.class)
    public void findOne_invalidAccidentStatistics_shouldThrowException() throws Exception {
        accidentStatisticsService.findOne(-1L);
    }

    @Test
    public void findAll_populatedDatabase_shouldReturnAccidentStatistics() throws Exception {
        Long id1 = 0L;
        AccidentStatistics accidentStatistics1 = buildValidAccidentStatistics(id1);
        assertThat(accidentStatisticsService.save(accidentStatistics1), is(accidentStatistics1));
        Long id2 = 1L;
        AccidentStatistics accidentStatistics2 = buildValidAccidentStatistics(id2);
        assertThat(accidentStatisticsService.save(accidentStatistics2), is(accidentStatistics2));
        List<AccidentStatistics> accidentStatistics = Arrays.asList(accidentStatistics1, accidentStatistics2);
        assertThat(accidentStatisticsService.findAll(), is(accidentStatistics));
        accidentStatisticsService.delete(id1); //cleanup
        accidentStatisticsService.delete(id2); //cleanup
    }

    @Test
    public void findAll_emptyDatabase_shouldReturnEmptyList() throws Exception {
        assertThat(accidentStatisticsService.findAll(), IsEmptyCollection.empty());
    }

    public AccidentStatistics buildValidAccidentStatistics(Long id) {
        BigDecimal lat = BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_UP);
        BigDecimal lon = BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_UP);
        return AccidentStatistics.builder()
                .id(id)
                .vin("W0L000051T2123456")
                .modelType("Opel")
                .passengers(4)
                .location(new GpsPoint(lat, lon))
                .arrivalTimeEmergencyService(5)
                .clearanceTimeAccidentSpot(30)
                .build();
    }

}
