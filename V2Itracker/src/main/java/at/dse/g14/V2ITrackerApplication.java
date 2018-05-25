package at.dse.g14;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author Michael Sober
 * @version ${buildVersion}
 * @since 1.0.0
 */
@SpringBootApplication
@EnableCircuitBreaker
@EnableFeignClients
@EnableDiscoveryClient
public class V2ITrackerApplication {

  public static void main(String[] args) {
    SpringApplication.run(V2ITrackerApplication.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

}