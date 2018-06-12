package at.dse.g14.data.simulator.scenario;

import at.dse.g14.commons.dto.ArrivalEventDTO;
import at.dse.g14.commons.dto.ClearanceEventDTO;
import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.commons.dto.VehicleTrackDTO;
import at.dse.g14.data.simulator.web.DseSender;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Slf4j
public class Scenario1Random extends AbstractScenario {

  private static final int ARRIVAL_TIME_SEC = 60;
  private static final int CLEARANCE_TIME_SEC = 10;

  private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

  private final Map<Vehicle, VehicleTrackDTO> crashes;

  public Scenario1Random(final DseSender sender) {
    super(sender);

    crashes = new HashMap<>();
  }

  @Override
  public void run() {
    log.info("run");
    executor.scheduleAtFixedRate(this::simulateTrackData, 5, 2, TimeUnit.SECONDS);
  }

  public void simulateTrackData() {
    try {

      final ThreadLocalRandom random = ThreadLocalRandom.current();

      for (Entry<Vehicle, CSVReader> entry : vehicleDataMap.entrySet()) {
        String[] line = entry.getValue().readNext();

        if (line.length != 8) {
          log.warn("line invalid, skip it");
          break;
        }

        if (!crashes.containsKey(entry.getKey())) {
          final VehicleTrackDTO track =
              VehicleTrackDTO.builder()
                  .vin(String.valueOf(entry.getKey().getVin()))
                  .modelType(entry.getKey().getModelType())
                  .passengers(random.nextInt(1, 4))
                  .location(constructGpsPoint(line))
                  .speed(BigDecimal.valueOf(Double.parseDouble(line[7])))
                  .distanceVehicleAhead(BigDecimal.valueOf(random.nextLong(0, 500)))
                  .distanceVehicleBehind(BigDecimal.valueOf(random.nextLong(0, 500)))
                  .nearCrashEvent(getRandomNearCrashEvent(random))
                  .crashEvent(getRandomCrashEvent(random))
                  .build();

          sender.sendTrackData(track);

          if (track.getCrashEvent()) {
            log.info("crash event detected!");
            crashes.put(entry.getKey(), track);
            executor.schedule(
                () -> handleCrashStart(entry.getKey()), ARRIVAL_TIME_SEC, TimeUnit.SECONDS);
          }
        }
      }

    } catch (IOException e) {
      log.error("error reading csv", e);
    }
  }

  private void handleCrashStart(final Vehicle vehicle) {
    executor.schedule(() -> handleCrashOver(vehicle), CLEARANCE_TIME_SEC, TimeUnit.SECONDS);
    final List<Vehicle> vehicles =
        sender.getVehicleToNotify(crashes.get(vehicle).getLocation(), 10);
    sender.sendEvent(new ArrivalEventDTO(vehicles));
  }

  private void handleCrashOver(final Vehicle vehicle) {
    final List<Vehicle> vehicles =
        sender.getVehicleToNotify(crashes.get(vehicle).getLocation(), 10);
    sender.sendEvent(new ClearanceEventDTO(vehicles));
    crashes.remove(vehicle);
    log.info("crash over");
  }
}
