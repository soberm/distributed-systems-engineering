package at.dse.g14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
public class FeignExampleController {

    private static Logger log = LoggerFactory.getLogger(FeignExampleController.class);

    @RequestMapping(value = "/greeting")
    public String greet() {
        log.info("Access /greeting");

        List<String> greetings = Arrays.asList("Hi there", "Greetings", "Salutations");
        Random rand = new Random();

        int randomNum = rand.nextInt(greetings.size());
        return greetings.get(randomNum);
    }

    @RequestMapping(value = "/hi_getall")
    public List<String> getall() {
        log.info("Access /getall");

        List<String> greetings = Arrays.asList("Hi there", "Greetings", "Salutations");
        return greetings;
    }

    @RequestMapping(value = "/")
    public String home() {
        log.info("Access /");
        return "Hi!";
    }
}