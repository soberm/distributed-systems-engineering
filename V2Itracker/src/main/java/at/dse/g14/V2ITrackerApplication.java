package at.dse.g14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * An application, which is responsible for saving VehicleTracks and analysing them.
 *
 * @author Michael Sober
 * @since 1.0
 */
@EnableSwagger2
@SpringBootApplication
@EnableCircuitBreaker
@EnableFeignClients
public class V2ITrackerApplication {

  public static void main(String[] args) {
    SpringApplication.run(V2ITrackerApplication.class, args);
  }
}
