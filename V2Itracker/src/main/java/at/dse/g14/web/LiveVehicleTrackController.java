package at.dse.g14.web;

import at.dse.g14.commons.dto.RangeRequest;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.LiveVehicleTrack;
import at.dse.g14.service.ILiveVehicleTrackService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/live-vehicle-track")
public class LiveVehicleTrackController {

  private ILiveVehicleTrackService liveVehicleTrackService;

  @Autowired
  public LiveVehicleTrackController(ILiveVehicleTrackService liveVehicleTrackService) {
    this.liveVehicleTrackService = liveVehicleTrackService;
  }

  @GetMapping
  public List<LiveVehicleTrack> getLiveVehicleTracks(
      @RequestParam(required = false) String manufacturer) throws ServiceException {
    if (manufacturer != null) {
      return liveVehicleTrackService.findByManufacturer(manufacturer);
    }
    return liveVehicleTrackService.findAll();
  }

  @PostMapping("/range")
  public List<LiveVehicleTrack> getLiveVehicleTracksInRange(@RequestBody RangeRequest rangeRequest)
      throws ServiceException {
    log.info("{} arrived.", rangeRequest);
    Double[] location = rangeRequest.getLocation();
    Point requestedPoint = new Point(location[0], location[1]);
    Distance rangeToOtherVehicles =
        new Distance(rangeRequest.getRangeKilometre().doubleValue(), Metrics.KILOMETERS);

    return liveVehicleTrackService.findByLocationNear(requestedPoint, rangeToOtherVehicles);
  }
}
