package at.dse.g14;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
public interface GreetingController {

  @GetMapping("/demo/greeting/{username}")
  String greeting(@PathVariable("username") String username);
}
