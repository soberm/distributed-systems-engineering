package at.dse.g14.web;

import at.dse.g14.commons.dto.LiveData;
import at.dse.g14.entity.LiveDataEntity;
import at.dse.g14.entity.VehicleEntity;
import at.dse.g14.entity.VehicleManufacturerEntity;
import at.dse.g14.persistence.VehicleManufacturerRepository;
import at.dse.g14.persistence.VehicleRepository;
import at.dse.g14.service.LiveDataService;
import at.dse.g14.service.exception.ValidationException;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@RestController
@RequestMapping("/manufacturer/{id}/vehicle/{vid}/livedata")
public class LiveDataController {

  private final LiveDataService dataService;
  private final VehicleRepository vehicleRepository;
  private final VehicleManufacturerRepository manufacturerRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public LiveDataController(
      final LiveDataService dataService,
      final VehicleRepository vehicleRepository,
      final VehicleManufacturerRepository manufacturerRepository,
      final ModelMapper modelMapper) {
    this.dataService = dataService;
    this.vehicleRepository = vehicleRepository;
    this.manufacturerRepository = manufacturerRepository;
    this.modelMapper = modelMapper;
  }

  @PostMapping
  public LiveData createVehicle(@RequestBody final LiveData data) throws ValidationException {
    return dataService.create(data);
  }

  @GetMapping("/{vid}")
  public ResponseEntity<Set<LiveData>> getVehicleData(
      @PathVariable("id") final long id, @PathVariable("vid") final long vid) {
    final VehicleEntity vehicleEntity = vehicleRepository.findOne(vid);
    final VehicleManufacturerEntity manufacturerEntity = manufacturerRepository.findOne(id);

    return vehicleEntity.getManufacturer().equals(manufacturerEntity)
        ? new ResponseEntity<>(convertToDto(vehicleEntity.getData()), HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  private LiveData convertToDto(final LiveDataEntity entity) {
    return modelMapper.map(entity, LiveData.class);
  }

  private Set<LiveData> convertToDto(final Set<LiveDataEntity> entities) {
    return entities.stream().map(this::convertToDto).collect(Collectors.toSet());
  }
}
