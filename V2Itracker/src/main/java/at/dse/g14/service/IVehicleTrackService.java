package at.dse.g14.service;

import at.dse.g14.commons.service.CrudService;
import at.dse.g14.entity.VehicleTrack;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import java.util.List;

public interface IVehicleTrackService extends CrudService<VehicleTrack, Long> {

    List<VehicleTrack> findByLocationNear(Point p, Distance d);

}
