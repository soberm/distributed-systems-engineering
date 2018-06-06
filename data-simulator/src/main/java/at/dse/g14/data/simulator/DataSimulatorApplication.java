package at.dse.g14.data.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@SpringBootApplication
//@EnableDiscoveryClient
@EnableScheduling
public class DataSimulatorApplication {

  public static void main(String[] args) {
    SpringApplication.run(DataSimulatorApplication.class, args);
  }

}
