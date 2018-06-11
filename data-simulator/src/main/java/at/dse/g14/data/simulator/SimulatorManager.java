package at.dse.g14.data.simulator;

import at.dse.g14.data.simulator.scenario.Scenario;
import at.dse.g14.data.simulator.scenario.Scenario1Random;
import org.springframework.stereotype.Service;

@Service
public class SimulatorManager {

  final DseSender dseSender;

  public SimulatorManager(final DseSender dseSender) {
    this.dseSender = dseSender;
  }

  public void start() {
    final Scenario scenario = new Scenario1Random(dseSender);
//  final Scenario scenario = new Scenario2Crash(dseSender);

    scenario.init();

    Thread thread = new Thread(scenario);
    thread.start();
  }
}
