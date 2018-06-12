package at.dse.g14.data.simulator.scenario;

import at.dse.g14.commons.dto.AccidentStatisticsDTO;
import at.dse.g14.commons.dto.data.Vehicle;
import at.dse.g14.commons.dto.events.ArrivalEventDTO;
import at.dse.g14.commons.dto.events.ClearanceEventDTO;
import at.dse.g14.commons.dto.track.VehicleTrackDTO;
import at.dse.g14.data.simulator.web.DseSender;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Scenario2Crash extends AbstractScenario {

  private static final int ARRIVAL_TIME_SEC = 20;
  private static final int CLEARANCE_TIME_SEC = 10;
  private static final int START_INTERVALL_MSEC = 2000;
  private static final int SEND_INTERVALL_MSEC = 500;

  private Vehicle crashedVehicle;
  private AccidentStatisticsDTO accidentStatistics;
  private long crashStartTime;

  public Scenario2Crash(final DseSender sender) {
    super(sender);
  }

  @Override
  public void run() {
    long i = 1;
    for (Entry<Vehicle, CSVReader> entry : vehicleDataMap.entrySet()) {
      executor.scheduleAtFixedRate(
          () -> startVehicle(entry), START_INTERVALL_MSEC * i, SEND_INTERVALL_MSEC,
          TimeUnit.SECONDS);
      i += 1;
    }
  }

  private void startVehicle(final Entry<Vehicle, CSVReader> entry) {
    try {
      final Vehicle vehicle = entry.getKey();
      final CSVReader reader = entry.getValue();

      final ThreadLocalRandom random = ThreadLocalRandom.current();
      String[] line = reader.readNext();

      if (line.length != 8) {
        log.warn("line invalid, skip it");
        return;
      }

      if (vehicle != crashedVehicle) {
        final VehicleTrackDTO track =
            VehicleTrackDTO.builder()
                .vin(String.valueOf(vehicle.getVin()))
                .modelType(vehicle.getModelType())
                .passengers(random.nextInt(1, 4))
                .location(constructGpsPoint(line))
                .speed(BigDecimal.valueOf(Double.parseDouble(line[7])))
                .distanceVehicleAhead(BigDecimal.valueOf(random.nextLong(0, 500)))
                .distanceVehicleBehind(BigDecimal.valueOf(random.nextLong(0, 500)))
                .nearCrashEvent(getRandomNearCrashEvent(random))
                .crashEvent(crashedVehicle == null && getRandomCrashEvent(random))
                .build();

        sender.sendTrackData(track);

        if (track.getCrashEvent()) {
          log.info("crash event detected!");
          initCrash(vehicle, track);
        }
      }
    } catch (IOException e) {
      log.error("error reading csv", e);
    }
  }

  private void initCrash(final Vehicle vehicle, final VehicleTrackDTO track) {
    crashedVehicle = vehicle;
    crashStartTime = System.currentTimeMillis();

    accidentStatistics =
        AccidentStatisticsDTO.builder()
            .vin(vehicle.getVin())
            .modelType(vehicle.getModelType())
            .location(track.getLocation())
            .passengers(track.getPassengers())
            .arrivalTimeEmergencyService(0)
            .clearanceTimeAccidentSpot(0)
            .build();

    executor.schedule(() -> handleCrashStart(track), ARRIVAL_TIME_SEC, TimeUnit.SECONDS);
  }

  private void handleCrashStart(final VehicleTrackDTO track) {
    final List<Vehicle> vehicles = sender.getVehicleToNotify(track.getLocation(), 10);
    sender.sendEvent(new ArrivalEventDTO(vehicles));

    final long currentTime = System.currentTimeMillis();
    accidentStatistics.setArrivalTimeEmergencyService((int) (currentTime - crashStartTime));

    executor.schedule(() -> handleCrashOver(track), CLEARANCE_TIME_SEC, TimeUnit.SECONDS);
  }

  private void handleCrashOver(final VehicleTrackDTO track) {
    final List<Vehicle> vehicles = sender.getVehicleToNotify(track.getLocation(), 10);
    sender.sendEvent(new ClearanceEventDTO(vehicles));

    final long currentTime = System.currentTimeMillis();
    accidentStatistics.setClearanceTimeAccidentSpot((int) (currentTime - crashStartTime));

    sender.sendStatistics(accidentStatistics);

    crashedVehicle = null;
    crashStartTime = 0;
    accidentStatistics = null;

    log.info("crash over");
  }
}
