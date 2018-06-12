package at.dse.g14.data.simulator;

import at.dse.g14.data.simulator.scenario.AbstractScenario;
import at.dse.g14.data.simulator.scenario.Scenario1Random;
import at.dse.g14.data.simulator.scenario.Scenario2Crash;
import at.dse.g14.data.simulator.web.DseSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SimulatorManager {

  private final DseSender dseSender;
  private AbstractScenario currentScenario;

  public SimulatorManager(final DseSender dseSender) {
    this.dseSender = dseSender;
  }

  public void startScenario() {
    currentScenario = new Scenario1Random(dseSender);
    //  currentScenario = new Scenario2Crash(dseSender);

    currentScenario.init();

    Thread thread = new Thread(currentScenario);
    thread.start();
  }

  public void startScenario(final int id) {
    currentScenario.stop();

    switch (id) {
      case 1:
        currentScenario = new Scenario1Random(dseSender);
        break;
      case 2:
        currentScenario = new Scenario2Crash(dseSender);
        break;
      default:
        log.info("unknown scenario ID");
    }

    currentScenario.init();

    Thread thread = new Thread(currentScenario);
    thread.start();
  }

  public void stopScenario() {
    currentScenario.stop();
  }
}
