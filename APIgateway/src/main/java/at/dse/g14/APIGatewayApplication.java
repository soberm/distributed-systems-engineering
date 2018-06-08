package at.dse.g14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@SpringBootApplication
@EnableZuulProxy
//@EnableDiscoveryClient
@EnableCircuitBreaker
public class APIGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(APIGatewayApplication.class, args);
  }
}
