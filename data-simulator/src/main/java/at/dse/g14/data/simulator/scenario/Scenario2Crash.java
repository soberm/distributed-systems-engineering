package at.dse.g14.data.simulator.scenario;

import at.dse.g14.commons.dto.ArrivalEventDTO;
import at.dse.g14.commons.dto.ClearanceEventDTO;
import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.commons.dto.VehicleTrackDTO;
import at.dse.g14.data.simulator.DseSender;
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
  private static final int START_INTERVALL_SEC = 10;
  private static final int SEND_INTERVALL_SEC = 5;

  private Vehicle crashedVehicle;

  public Scenario2Crash(final DseSender sender) {
    super(sender);
  }

  @Override
  public void run() {
    long i = 1;
    for (Entry<Vehicle, CSVReader> entry : vehicleDataMap.entrySet()) {
      executor.scheduleAtFixedRate(
          () -> startVehicle(entry), START_INTERVALL_SEC * i, SEND_INTERVALL_SEC, TimeUnit.SECONDS);
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
          crashedVehicle = vehicle;
          handleCrashStart(track);
          executor.schedule(() -> handleCrashStart(track), ARRIVAL_TIME_SEC, TimeUnit.SECONDS);
        }
      }
    } catch (IOException e) {
      log.error("error reading csv", e);
    }
  }

  private void handleCrashStart(final VehicleTrackDTO track) {
    executor.schedule(() -> handleCrashOver(track), CLEARANCE_TIME_SEC, TimeUnit.SECONDS);
    sender.sendEvent(new ArrivalEventDTO(true));
  }

  private void handleCrashOver(final VehicleTrackDTO track) {
    final List<Vehicle> vehicles = sender.getVehicleToNotify(track.getLocation(), 10);
    sender.sendEvent(new ClearanceEventDTO(true, vehicles));
    crashedVehicle = null;
    log.info("crash over");
  }
}
