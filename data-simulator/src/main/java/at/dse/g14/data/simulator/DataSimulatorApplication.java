package at.dse.g14.data.simulator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@SpringBootApplication
//@EnableDiscoveryClient
@EnableScheduling
@Component
@Slf4j
public class DataSimulatorApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(DataSimulatorApplication.class, args);
  }

  @Autowired
  private SimulatorManager simulatorManager;

  @Override
  public void run(String... args) throws Exception {
    log.info("start");
    simulatorManager.startScenario();
  }
}
