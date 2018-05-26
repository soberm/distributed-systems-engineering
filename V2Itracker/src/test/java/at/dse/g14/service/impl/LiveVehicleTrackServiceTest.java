package at.dse.g14.service.impl;

import at.dse.g14.entity.LiveVehicleTrack;
import at.dse.g14.service.ILiveVehicleTrackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LiveVehicleTrackServiceTest {

    @Autowired
    private ILiveVehicleTrackService liveVehicleTrackService;

    @Test
    public void findByLocationWithin_populatedDatabase_shouldReturnVehicleTracks() throws Exception {
        String vin1 = "W0L002051T2123456";
        LiveVehicleTrack liveVehicleTrack1 = buildValidVehicleTrack(vin1);
        liveVehicleTrack1.setLocation(new Double[]{48.208174, 16.373819}); //Vienna
        assertThat(liveVehicleTrackService.save(liveVehicleTrack1), is(liveVehicleTrack1));

        String vin2 = "W0L000151T2123456";
        LiveVehicleTrack liveVehicleTrack2 = buildValidVehicleTrack(vin2);
        liveVehicleTrack2.setLocation(new Double[]{48.309667, 16.322878}); //Klosterneuburg
        assertThat(liveVehicleTrackService.save(liveVehicleTrack2), is(liveVehicleTrack2));

        String vin3 = "W0L003451T2123456";
        LiveVehicleTrack liveVehicleTrack3 = buildValidVehicleTrack(vin3);
        liveVehicleTrack3.setLocation(new Double[]{48.198591, 16.363106}); //Vienna
        assertThat(liveVehicleTrackService.save(liveVehicleTrack3), is(liveVehicleTrack3));

        Double[] location = liveVehicleTrack1.getLocation();
        List<LiveVehicleTrack> vehicleTracksInRange = Arrays.asList(liveVehicleTrack1, liveVehicleTrack3);

        Point point = new Point(location[0], location[1]);
        Distance distance = new Distance(10, Metrics.KILOMETERS);
        assertThat(liveVehicleTrackService.findByLocationNear(point, distance), is(vehicleTracksInRange));

        liveVehicleTrackService.delete(vin1); // cleanup
        liveVehicleTrackService.delete(vin2); // cleanup
        liveVehicleTrackService.delete(vin3); // cleanup
    }

    public LiveVehicleTrack buildValidVehicleTrack(String vin) {
        return LiveVehicleTrack.builder()
                .vin(vin)
                .modelType("Opel")
                .passengers(4)
                .location(new Double[]{20.0, 20.0})
                .speed(new BigDecimal(130))
                .distanceVehicleAhead(new BigDecimal(20))
                .distanceVehicleBehind(new BigDecimal(15))
                .nearCrashEvent(false)
                .crashEvent(false)
                .build();
    }

}
