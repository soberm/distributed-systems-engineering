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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Scenario2Crash extends AbstractScenario {

  private static final int ARRIVAL_TIME_SEC = 20;
  private static final int CLEARANCE_TIME_SEC = 10;
  private static final long START_INTERVALL_MSEC = 2000;
  private static final long SEND_INTERVALL_MSEC = 500;

  private final Map<Vehicle, CSVReader> cars1 = new HashMap<>();
  private final Map<Vehicle, CSVReader> cars2 = new HashMap<>();
  private final Map<Vehicle, CSVReader> cars3 = new HashMap<>();

  private Vehicle crashedVehicle;
  private AccidentStatisticsDTO accidentStatistics;
  private long crashStartTime;

  public Scenario2Crash(final DseSender sender) {
    super(sender);

    cars1.put(vehicle1, car1);
    cars2.put(vehicle2, car1);
    cars3.put(vehicle3, car1);

    cars1.put(vehicle7, car7);
    cars2.put(vehicle8, car7);
    cars3.put(vehicle9, car7);

    cars1.put(vehicle13, car13);
    cars2.put(vehicle14, car13);
    cars3.put(vehicle15, car13);
  }

  @Override
  public void run() {

    final AtomicInteger i = new AtomicInteger();
    cars1.forEach(
        (key, value) ->
            executor.scheduleAtFixedRate(
                () -> startVehicle(key, value),
                START_INTERVALL_MSEC* i.getAndIncrement(),
                SEND_INTERVALL_MSEC,
                TimeUnit.MILLISECONDS));
    i.set(0);

    cars2.forEach(
        (key, value) ->
            executor.scheduleAtFixedRate(
                () -> startVehicle(key, value),
                START_INTERVALL_MSEC* i.getAndIncrement(),
                SEND_INTERVALL_MSEC,
                TimeUnit.MILLISECONDS));
    i.set(0);

    cars3.forEach(
        (key, value) ->
            executor.scheduleAtFixedRate(
                () -> startVehicle(key, value),
                START_INTERVALL_MSEC* i.getAndIncrement(),
                SEND_INTERVALL_MSEC,
                TimeUnit.MILLISECONDS));
  }

  private void startVehicle(final Vehicle vehicle, final CSVReader reader) {

    try {
      if (vehicle != crashedVehicle) {

        final ThreadLocalRandom random = ThreadLocalRandom.current();
        String[] line = reader.readNext();

        if (line.length != 8) {
          log.warn("line invalid, skip it");
          return;
        }

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
