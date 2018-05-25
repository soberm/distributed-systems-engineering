package at.dse.g14.service.impl;


import at.dse.g14.commons.dto.AccidentStatistics;
import at.dse.g14.commons.dto.GpsPoint;
import at.dse.g14.commons.service.exception.ValidationException;
import at.dse.g14.service.IAccidentStatisticsService;
import at.dse.g14.service.exception.AccidentStatisticsAlreadyExistsException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
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

    public AccidentStatistics buildValidAccidentStatistics(Long id) {
        return AccidentStatistics.builder()
                .id(id)
                .vin("W0L000051T2123456")
                .modelType("Opel")
                .passengers(4)
                .location(new GpsPoint(new BigDecimal(20), new BigDecimal(20)))
                .arrivalTimeEmergencyService(5)
                .clearanceTimeAccidentSpot(30)
                .build();
    }

}
