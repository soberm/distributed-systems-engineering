package at.dse.g14.service;

import at.dse.g14.commons.service.CrudService;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.LiveVehicleTrack;
import java.util.List;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

public interface ILiveVehicleTrackService extends CrudService<LiveVehicleTrack, String> {

  List<LiveVehicleTrack> findByLocationNear(Point p, Distance d) throws ServiceException;

  List<LiveVehicleTrack> findByManufacturer(String id) throws ServiceException;
}
