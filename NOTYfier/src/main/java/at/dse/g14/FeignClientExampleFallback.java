package at.dse.g14;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

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
