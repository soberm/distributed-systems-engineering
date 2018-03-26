package at.dse.g14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@EnableEurekaServer
@SpringBootApplication
public class RegistryApplication {

  public static void main(String[] args) {
    SpringApplication.run(RegistryApplication.class, args);
  }

}
