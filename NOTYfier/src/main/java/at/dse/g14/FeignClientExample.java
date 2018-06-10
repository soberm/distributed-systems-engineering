package at.dse.g14;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;

@FeignClient(name = "vehicledata-service", url = "${vehicledata.address}", fallback = FeignClientExampleFallback.class)
public interface FeignClientExample {

    @RequestMapping(method = RequestMethod.GET, value = "/hi_getall", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<String> getGreetings();

    @RequestMapping(method = RequestMethod.GET, value = "/greeting", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String getGreet();
}

@Component
class FeignClientExampleFallback implements FeignClientExample {
    @Override
    public List<String> getGreetings() {
        return Arrays.asList("Hello", "tryGetGreetingsElse");
    }

    @Override
    public String getGreet() {
        return "Greet fallback";
    }
}