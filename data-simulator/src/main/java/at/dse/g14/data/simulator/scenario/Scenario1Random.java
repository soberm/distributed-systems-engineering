package at.dse.g14.data.simulator.scenario;

import at.dse.g14.commons.dto.EmergencyService;
import at.dse.g14.commons.dto.Vehicle;
import at.dse.g14.commons.dto.VehicleManufacturer;
import at.dse.g14.commons.dto.VehicleTrackDTO;
import at.dse.g14.data.simulator.DseSender;
import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
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
public class Scenario1Random implements Scenario {

  private static final double NEAR_CRASH_EVENT_PROBABILITY = 0.2;
  private static final double CRASH_EVENT_PROBABILITY = 0.1;
  private static final int CRASH_COUNTER_MAX = 10;

  private CSVReader car1;
  private CSVReader car2;
  private CSVReader car3;
  private CSVReader car4;
  private CSVReader car5;
  private CSVReader car6;

  private final DseSender sender;

  private final Map<Vehicle, CSVReader> vehicleDataMap;
  private final Map<Vehicle, Integer> crashes;

  public Scenario1Random(final DseSender sender) {
    this.sender = sender;

    vehicleDataMap = new HashMap<>();
    crashes = new HashMap<>();
  }

  public void init() {
    log.info("init reader");

    car1 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car1.csv"))));
    car2 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car2.csv"))));
    car3 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car3.csv"))));
    car4 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car4.csv"))));
    car5 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car5.csv"))));
    car6 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car6.csv"))));

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

  private void createInitData() {
    log.info("create init data");
    VehicleManufacturer manufacturer1 = new VehicleManufacturer(null, "BMW");
    VehicleManufacturer manufacturer2 = new VehicleManufacturer(null, "VW");
    VehicleManufacturer manufacturer3 = new VehicleManufacturer(null, "Tesla");

    manufacturer1 = sender.createManufacturer(manufacturer1);
    manufacturer2 = sender.createManufacturer(manufacturer2);
    manufacturer3 = sender.createManufacturer(manufacturer3);

    Vehicle vehicle1 = new Vehicle(null, "Polo", manufacturer1);
    Vehicle vehicle2 = new Vehicle(null, "Golf", manufacturer1);
    Vehicle vehicle3 = new Vehicle(null, "2er Cabrio", manufacturer2);
    Vehicle vehicle4 = new Vehicle(null, "2er Coupe", manufacturer2);
    Vehicle vehicle5 = new Vehicle(null, "Model S", manufacturer3);
    Vehicle vehicle6 = new Vehicle(null, "Model X", manufacturer3);

    vehicle1 = sender.createVehicle(vehicle1);
    vehicle2 = sender.createVehicle(vehicle2);
    vehicle3 = sender.createVehicle(vehicle3);
    vehicle4 = sender.createVehicle(vehicle4);
    vehicle5 = sender.createVehicle(vehicle5);
    vehicle6 = sender.createVehicle(vehicle6);

    EmergencyService service1 = new EmergencyService(null, "Polizei");
    EmergencyService service2 = new EmergencyService(null, "Feuerwehr");
    EmergencyService service3 = new EmergencyService(null, "Rettung");

    service1 = sender.createEmergencyService(service1);
    service2 = sender.createEmergencyService(service2);
    service3 = sender.createEmergencyService(service3);

    vehicleDataMap.putIfAbsent(vehicle1, car1);
//    vehicleDataMap.putIfAbsent(vehicle2, car2);
//    vehicleDataMap.putIfAbsent(vehicle3, car3);
//    vehicleDataMap.putIfAbsent(vehicle4, car4);
//    vehicleDataMap.putIfAbsent(vehicle5, car5);
//    vehicleDataMap.putIfAbsent(vehicle6, car6);
  }

  @Override
  public void run() {
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
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

  private Double[] constructGpsPoint(final String[] line) {
    return new Double[]{
        Double.parseDouble(line[2]),
        Double.parseDouble(line[3])
    };
  }
}