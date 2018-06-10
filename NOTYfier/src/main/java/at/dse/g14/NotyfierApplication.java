package at.dse.g14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michael Sober
 * @version ${buildVersion}
 * @since 1.0.0
 */
@SpringBootApplication
@EnableCircuitBreaker
@EnableFeignClients
@RestController
//@EnableDiscoveryClient
public class NotyfierApplication {

  @RequestMapping(value = "/information")
  public String available() {
    return "Here is information from the NOTYfier service";
  }

  public static void main(String[] args) {
    SpringApplication.run(NotyfierApplication.class, args);
  }
}
