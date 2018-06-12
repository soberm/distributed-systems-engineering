package at.dse.g14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * An application, which is responsible for saving Notifications.
 *
 * @author Michael Sober
 * @since 1.0
 */
@EnableSwagger2
@SpringBootApplication
@EnableCircuitBreaker
@EnableFeignClients
public class NotyfierApplication {

  public static void main(String[] args) {
    SpringApplication.run(NotyfierApplication.class, args);
  }
}
