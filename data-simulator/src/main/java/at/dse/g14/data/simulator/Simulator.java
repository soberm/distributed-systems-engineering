package at.dse.g14.data.simulator;

import at.dse.g14.commons.dto.EmergencyService;
import at.dse.g14.commons.dto.GpsPoint;
import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.commons.dto.VehicleManufacturer;
import at.dse.g14.commons.dto.VehicleTrack;
import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Service
@Slf4j
public class Simulator {

  private static final double NEAR_CRASH_EVENT_PROBABILITY = 0.2;
  private static final double CRASH_EVENT_PROBABILITY = 0.1;
  private static final int CRASH_COUNTER_MAX = 10;
  private CSVReader car1;
  private CSVReader car2;
  private CSVReader car3;
  private CSVReader car4;
  private CSVReader car5;
  private CSVReader car6;
  private DseSender sender;
  private Map<Vehicle, CSVReader> vehicleDataMap;
  private Map<Vehicle, Integer> crashes = new HashMap<>();

  @PostConstruct
  public void init() {
    log.info("init reader");

    car1 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("./data/car1.csv"))));
    car2 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("./data/car2.csv"))));
    car3 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("./data/car3.csv"))));
    car4 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("./data/car4.csv"))));
    car5 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("./data/car5.csv"))));
    car6 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("./data/car6.csv"))));
    sender = new DseSender();

    final TimerTask timerTask =
        new TimerTask() {
          @Override
          public void run() {
            createInitData();
          }
        };
    final Timer timer = new Timer("Timer");
    timer.schedule(timerTask, 2000L);
  }

  public void createInitData() {
    log.info("create init data");
    final VehicleManufacturer manufacturer1 = new VehicleManufacturer(null, "BMW", new HashSet<>());
    final VehicleManufacturer manufacturer2 = new VehicleManufacturer(null, "VW", new HashSet<>());
    final VehicleManufacturer manufacturer3 =
        new VehicleManufacturer(null, "Tesla", new HashSet<>());

    sender.createManufacturer(manufacturer1);
    sender.createManufacturer(manufacturer2);
    sender.createManufacturer(manufacturer3);

    final Vehicle vehicle1 = new Vehicle(null, "Polo", manufacturer1);
    final Vehicle vehicle2 = new Vehicle(null, "Golf", manufacturer1);
    final Vehicle vehicle3 = new Vehicle(null, "2er Cabrio", manufacturer2);
    final Vehicle vehicle4 = new Vehicle(null, "2er Coupe", manufacturer2);
    final Vehicle vehicle5 = new Vehicle(null, "Model S", manufacturer3);
    final Vehicle vehicle6 = new Vehicle(null, "Model X", manufacturer3);

    sender.createVehicle(vehicle1);
    sender.createVehicle(vehicle2);
    sender.createVehicle(vehicle3);
    sender.createVehicle(vehicle4);
    sender.createVehicle(vehicle5);
    sender.createVehicle(vehicle6);

    final EmergencyService service1 = new EmergencyService(null, "Polizei");
    final EmergencyService service2 = new EmergencyService(null, "Feuerwehr");
    final EmergencyService service3 = new EmergencyService(null, "Rettung");

    sender.createEmergencyService(service1);
    sender.createEmergencyService(service2);
    sender.createEmergencyService(service3);

    vehicleDataMap = new HashMap<>();
    vehicleDataMap.putIfAbsent(vehicle1, car1);
    vehicleDataMap.putIfAbsent(vehicle2, car2);
    vehicleDataMap.putIfAbsent(vehicle3, car3);
    vehicleDataMap.putIfAbsent(vehicle4, car4);
    vehicleDataMap.putIfAbsent(vehicle5, car5);
    vehicleDataMap.putIfAbsent(vehicle6, car6);
  }

  @Scheduled(fixedRate = 2000, initialDelay = 5000)
  public void simulateTrackData() {
    try {

      final ThreadLocalRandom random = ThreadLocalRandom.current();

      for (Entry<Vehicle, CSVReader> entry : vehicleDataMap.entrySet()) {
        String[] line = entry.getValue().readNext();

        if (line.length != 8) {
          log.warn("line invalid, skip it");
          break;
        }

        final VehicleTrack track =
            VehicleTrack.builder()
                .vin(String.valueOf(entry.getKey().getId()))
                .modelType(entry.getKey().getModelType())
                .passengers(random.nextInt(1, 4))
                .location(constructGpsPoint(line))
                .speed(BigDecimal.valueOf(Double.parseDouble(line[7])))
                .distanceVehicleAhead(BigDecimal.valueOf(random.nextLong(0, 500)))
                .distanceVehicleBehind(BigDecimal.valueOf(random.nextLong(0, 500)))
                .nearCrashEvent(getRandomNearCrashEvent(random))
                .crashEvent(getCrashEvent(entry.getKey(), random))
                .build();

        sender.sendTrackData(track);
      }

    } catch (IOException e) {
      log.error("error reading csv", e);
    }
  }

  private boolean getRandomNearCrashEvent(final ThreadLocalRandom random) {
    return random.nextFloat() < NEAR_CRASH_EVENT_PROBABILITY;
  }

  private boolean getRandomCrashEvent(final ThreadLocalRandom random) {
    return random.nextFloat() < CRASH_EVENT_PROBABILITY;
  }

  private boolean getCrashEvent(final Vehicle vehicle, final ThreadLocalRandom random) {
    final boolean isCrash;
    if (!crashes.containsKey(vehicle)) {
      isCrash = getRandomCrashEvent(random);
      if (isCrash) {
        log.info("new crash: {}", vehicle);
        crashes.put(vehicle, 1);
      }
    } else {
      int count = crashes.get(vehicle);
      if (count < CRASH_COUNTER_MAX) {
        isCrash = true;
        crashes.put(vehicle, ++count);
        log.info("vehicle crash count: {}", count);
      } else {
        isCrash = false;
        crashes.remove(vehicle);
        log.info("vehicle crashes no more {}", vehicle);
      }
    }
    return isCrash;
  }

  private GpsPoint constructGpsPoint(final String[] line) {
    return new GpsPoint(
        BigDecimal.valueOf(Double.parseDouble(line[2])),
        BigDecimal.valueOf(Double.parseDouble(line[3])));
  }
}
