package at.dse.g14;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeignExampleController {

  @Autowired private FeignClientExample feignClientExample;

  @RequestMapping("/getall-hi")
  public List<String> getAllGreetings() {
    List<String> greetings = feignClientExample.getGreetings();
    return greetings;
  }

  @RequestMapping("/hi")
  public String getGreet() {
    return feignClientExample.getGreet();
  }

  @GetMapping(value = "/information", produces = "application/json")
  @ResponseBody
  public String available() {
    return "{\"information\": \"Here it is\"}";
  }
}
