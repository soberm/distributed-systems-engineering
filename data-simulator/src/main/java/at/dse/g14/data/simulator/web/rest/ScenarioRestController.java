package at.dse.g14.data.simulator.web.rest;

import at.dse.g14.data.simulator.SimulatorManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/simulator")
public class ScenarioRestController {

  private final SimulatorManager simulatorManager;

  @Autowired
  public ScenarioRestController(SimulatorManager simulatorManager) {
    this.simulatorManager = simulatorManager;
  }

  @GetMapping("/start")
  public void startScenario() {
    simulatorManager.startScenario();
  }

  @GetMapping("/start")
  public void startScenario(@RequestParam final int id) {
    simulatorManager.startScenario(id);
  }

  @GetMapping("/stop")
  public void stopScenario() {
    simulatorManager.stopScenario();
  }
}
