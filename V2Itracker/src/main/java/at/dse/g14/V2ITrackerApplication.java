package at.dse.g14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Michael Sober
 * @version ${buildVersion}
 * @since 1.0.0
 */
@SpringBootApplication
@EnableCircuitBreaker
@EnableFeignClients
// @EnableDiscoveryClient
public class V2ITrackerApplication {

  public static void main(String[] args) {
    SpringApplication.run(V2ITrackerApplication.class, args);
  }
}
