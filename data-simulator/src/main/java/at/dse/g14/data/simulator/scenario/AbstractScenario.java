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

  private static final double NEAR_CRASH_EVENT_PROBABILITY = 0.01;
  private static final double CRASH_EVENT_PROBABILITY = 0.01;

  protected final DseSender sender;
  protected final ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
  protected final Map<Vehicle, CSVReader> vehicleDataMap;

  protected CSVReader car1;
  protected CSVReader car2;
  protected CSVReader car3;
  protected CSVReader car4;
  protected CSVReader car5;
  protected CSVReader car6;
  protected CSVReader car7;
  protected CSVReader car8;
  protected CSVReader car9;
  protected CSVReader car10;
  protected CSVReader car11;
  protected CSVReader car12;
  protected CSVReader car13;
  protected CSVReader car14;
  protected CSVReader car15;
  protected CSVReader car16;
  protected CSVReader car17;

  protected VehicleManufacturer manufacturer1;
  protected VehicleManufacturer manufacturer2;
  protected VehicleManufacturer manufacturer3;

  protected Vehicle vehicle1;
  protected Vehicle vehicle2;
  protected Vehicle vehicle3;
  protected Vehicle vehicle4;
  protected Vehicle vehicle5;
  protected Vehicle vehicle6;
  protected Vehicle vehicle7;
  protected Vehicle vehicle8;
  protected Vehicle vehicle9;
  protected Vehicle vehicle10;
  protected Vehicle vehicle11;
  protected Vehicle vehicle12;
  protected Vehicle vehicle13;
  protected Vehicle vehicle14;
  protected Vehicle vehicle15;
  protected Vehicle vehicle16;
  protected Vehicle vehicle17;

  protected EmergencyService service1;
  protected EmergencyService service2;
  protected EmergencyService service3;

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
    car7 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car7.csv"))));
    car8 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car8.csv"))));
    car9 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car9.csv"))));
    car10 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car10.csv"))));
    car11 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car11.csv"))));
    car12 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car12.csv"))));
    car13 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car13.csv"))));
    car14 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car14.csv"))));
    car15 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car15.csv"))));
    car16 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car16.csv"))));
    car17 =
        new CSVReader(
            new BufferedReader(
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("data/car17.csv"))));

    createInitData();
  }

  private void createInitData() {
    log.info("create init data");
    manufacturer1 = new VehicleManufacturer(null, "BMW");
    manufacturer2 = new VehicleManufacturer(null, "VW");
    manufacturer3 = new VehicleManufacturer(null, "Tesla");

    manufacturer1 = sender.createManufacturer(manufacturer1);
    manufacturer2 = sender.createManufacturer(manufacturer2);
    manufacturer3 = sender.createManufacturer(manufacturer3);

    vehicle1 = new Vehicle("vehicle1", "Polo", manufacturer1);
    vehicle2 = new Vehicle("vehicle2", "Polo", manufacturer1);
    vehicle3 = new Vehicle("vehicle3", "Polo", manufacturer1);
    vehicle4 = new Vehicle("vehicle4", "Golf", manufacturer1);
    vehicle5 = new Vehicle("vehicle5", "Golf", manufacturer1);
    vehicle6 = new Vehicle("vehicle6", "Golf", manufacturer1);

    vehicle7 = new Vehicle("vehicle7", "2er Cabrio", manufacturer2);
    vehicle8 = new Vehicle("vehicle8", "2er Cabrio", manufacturer2);
    vehicle9 = new Vehicle("vehicle9", "2er Cabrio", manufacturer2);
    vehicle10 = new Vehicle("vehicle10", "2er Coupe", manufacturer2);
    vehicle11 = new Vehicle("vehicle11", "2er Coupe", manufacturer2);
    vehicle12 = new Vehicle("vehicle12", "2er Coupe", manufacturer2);

    vehicle13 = new Vehicle("vehicle13", "Model S", manufacturer3);
    vehicle14 = new Vehicle("vehicle14", "Model S", manufacturer3);
    vehicle15 = new Vehicle("vehicle15", "Model X", manufacturer3);
    vehicle16 = new Vehicle("vehicle16", "Model X", manufacturer3);
    vehicle17 = new Vehicle("vehicle17", "Model X", manufacturer3);

    vehicle1 = sender.createVehicle(vehicle1);
    vehicle2 = sender.createVehicle(vehicle2);
    vehicle3 = sender.createVehicle(vehicle3);
    vehicle4 = sender.createVehicle(vehicle4);
    vehicle5 = sender.createVehicle(vehicle5);
    vehicle6 = sender.createVehicle(vehicle6);
    vehicle7 = sender.createVehicle(vehicle7);
    vehicle8 = sender.createVehicle(vehicle8);
    vehicle9 = sender.createVehicle(vehicle9);
    vehicle10 = sender.createVehicle(vehicle10);
    vehicle11 = sender.createVehicle(vehicle11);
    vehicle12 = sender.createVehicle(vehicle12);
    vehicle13 = sender.createVehicle(vehicle13);
    vehicle14 = sender.createVehicle(vehicle14);
    vehicle15 = sender.createVehicle(vehicle15);
    vehicle16 = sender.createVehicle(vehicle1);
    vehicle17 = sender.createVehicle(vehicle1);

    service1 = new EmergencyService(null, "Polizei");
    service2 = new EmergencyService(null, "Feuerwehr");
    service3 = new EmergencyService(null, "Rettung");

    service1 = sender.createEmergencyService(service1);
    service2 = sender.createEmergencyService(service2);
    service3 = sender.createEmergencyService(service3);

    vehicleDataMap.putIfAbsent(vehicle1, car1);
    vehicleDataMap.putIfAbsent(vehicle2, car2);
    vehicleDataMap.putIfAbsent(vehicle3, car3);
    vehicleDataMap.putIfAbsent(vehicle4, car4);
    vehicleDataMap.putIfAbsent(vehicle5, car5);
    vehicleDataMap.putIfAbsent(vehicle6, car6);
    vehicleDataMap.putIfAbsent(vehicle7, car7);
    vehicleDataMap.putIfAbsent(vehicle8, car8);
    vehicleDataMap.putIfAbsent(vehicle9, car9);
    vehicleDataMap.putIfAbsent(vehicle10, car10);
    vehicleDataMap.putIfAbsent(vehicle11, car11);
    vehicleDataMap.putIfAbsent(vehicle12, car12);
    vehicleDataMap.putIfAbsent(vehicle13, car13);
    vehicleDataMap.putIfAbsent(vehicle14, car14);
    vehicleDataMap.putIfAbsent(vehicle15, car15);
    vehicleDataMap.putIfAbsent(vehicle16, car16);
    vehicleDataMap.putIfAbsent(vehicle17, car17);
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
