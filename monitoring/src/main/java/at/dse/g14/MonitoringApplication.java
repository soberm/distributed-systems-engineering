package at.dse.g14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@SpringBootApplication
@EnableHystrixDashboard
public class MonitoringApplication {

  public static void main(String[] args) {
    SpringApplication.run(MonitoringApplication.class, args);
  }

}
