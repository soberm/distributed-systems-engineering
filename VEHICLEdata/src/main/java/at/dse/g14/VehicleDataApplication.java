package at.dse.g14;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@EnableSwagger2
@SpringBootApplication
@EnableCircuitBreaker
@EnableFeignClients
public class VehicleDataApplication {

  public static void main(String[] args) {
    SpringApplication.run(VehicleDataApplication.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
