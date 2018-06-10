package at.dse.g14;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

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