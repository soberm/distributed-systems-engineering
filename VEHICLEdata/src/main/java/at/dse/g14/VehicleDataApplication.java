package at.dse.g14;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@SpringBootApplication
@EnableCircuitBreaker
@EnableFeignClients
@EnableDiscoveryClient
public class VehicleDataApplication {

  public static void main(String[] args) {
    SpringApplication.run(VehicleDataApplication.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
