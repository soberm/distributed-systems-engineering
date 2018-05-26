package at.dse.g14.service;

import at.dse.g14.commons.service.CrudService;
import at.dse.g14.entity.LiveVehicleTrack;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import java.util.List;

public interface ILiveVehicleTrackService extends CrudService<LiveVehicleTrack, String> {

    List<LiveVehicleTrack> findByLocationNear(Point p, Distance d);

}
