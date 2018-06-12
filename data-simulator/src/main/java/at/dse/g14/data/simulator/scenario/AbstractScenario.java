package at.dse.g14.data.simulator.scenario;

import at.dse.g14.commons.dto.data.EmergencyService;
import at.dse.g14.commons.dto.data.Vehicle;
import at.dse.g14.commons.dto.data.VehicleManufacturer;
import at.dse.g14.data.simulator.web.DseSender;
import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractScenario implements Runnable {

  private static final double NEAR_CRASH_EVENT_PROBABILITY = 0.2;
  private static final double CRASH_EVENT_PROBABILITY = 0.1;

  protected final DseSender sender;
  protected final ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
  protected final Map<Vehicle, CSVReader> vehicleDataMap;

  protected CSVReader car1;
  protected CSVReader car2;
  protected CSVReader car3;
  protected CSVReader car4;
  protected CSVReader car5;
  protected CSVReader car6;

  protected AbstractScenario(DseSender sender) {
    this.sender = sender;
    vehicleDataMap = new HashMap<>();
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

    createInitData();
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
    vehicleDataMap.putIfAbsent(vehicle2, car2);
    vehicleDataMap.putIfAbsent(vehicle3, car3);
    vehicleDataMap.putIfAbsent(vehicle4, car4);
    vehicleDataMap.putIfAbsent(vehicle5, car5);
    vehicleDataMap.putIfAbsent(vehicle6, car6);
  }

  protected Double[] constructGpsPoint(final String[] line) {
    return new Double[]{Double.parseDouble(line[2]), Double.parseDouble(line[3])};
  }

  protected boolean getRandomNearCrashEvent(final ThreadLocalRandom random) {
    return random.nextFloat() < NEAR_CRASH_EVENT_PROBABILITY;
  }

  protected boolean getRandomCrashEvent(final ThreadLocalRandom random) {
    return random.nextFloat() < CRASH_EVENT_PROBABILITY;
  }

  public void stop() {
    log.info("stop scenario");
    executor.shutdown();
  }
}
