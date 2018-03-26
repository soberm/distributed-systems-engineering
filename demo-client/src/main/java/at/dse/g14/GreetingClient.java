package at.dse.g14;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@FeignClient(
    name = "demo-client",
    url = "http://localhost:9090",
    fallback = GreetingClient.GreetingClientFallback.class
)
public interface GreetingClient {

  @GetMapping("/demo/greeting/{username}")
  String greeting(@PathVariable("username") String username);

  @Component
  public static class GreetingClientFallback implements GreetingClient {

    @Override
    public String greeting(@PathVariable("username") String username) {
      return "Hello User!";
    }
  }
}
