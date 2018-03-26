package at.dse.g14;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Controller
public class GreetingController {

  @Autowired
  private GreetingClient greetingClient;

  @RequestMapping("/demo/get-greeting/{username}")
  public String getGreeting(Model model, @PathVariable("username") String username) {
    model.addAttribute("greeting", greetingClient.greeting(username));
    return "greeting-view";
  }
}