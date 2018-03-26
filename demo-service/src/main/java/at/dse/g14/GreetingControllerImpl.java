package at.dse.g14;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@RestController
public class GreetingControllerImpl implements GreetingController {

  @Override
  public String greeting(@PathVariable("username") String username) {
    return String.format("Hello %s!\n", username);
  }
}
