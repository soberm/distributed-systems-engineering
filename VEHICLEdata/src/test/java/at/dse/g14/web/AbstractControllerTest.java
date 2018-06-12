package at.dse.g14.web;

import at.dse.g14.commons.dto.data.Vehicle;
import at.dse.g14.commons.dto.data.VehicleManufacturer;
import at.dse.g14.entity.VehicleEntity;
import at.dse.g14.entity.VehicleManufacturerEntity;
import at.dse.g14.persistence.VehicleManufacturerRepository;
import at.dse.g14.persistence.VehicleRepository;
import com.google.gson.Gson;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

  @Autowired
  protected VehicleManufacturerRepository manufacturerRepository;
  @Autowired
  protected VehicleRepository vehicleRepository;
  @Autowired
  protected ModelMapper modelMapper;

  @Autowired
  protected MockMvc mockMvc;
  @Autowired
  protected Gson gson;

  protected VehicleManufacturerEntity manufacturerEntity1;
  protected VehicleManufacturerEntity manufacturerEntity2;
  protected VehicleManufacturerEntity manufacturerEntity3;

  protected VehicleEntity vehicleEntity1;
  protected VehicleEntity vehicleEntity2;
  protected VehicleEntity vehicleEntity3;
  protected VehicleEntity vehicleEntity4;
  protected VehicleEntity vehicleEntity5;
  protected VehicleEntity vehicleEntity6;

  @Before
  public void setup() {
    manufacturerEntity1 =
        manufacturerRepository.save(new VehicleManufacturerEntity(null, "BMW", new HashSet<>()));
    manufacturerEntity2 =
        manufacturerRepository.save(new VehicleManufacturerEntity(null, "VW", new HashSet<>()));
    manufacturerEntity3 =
        manufacturerRepository.save(new VehicleManufacturerEntity(null, "Tesla", new HashSet<>()));

    vehicleEntity1 = vehicleRepository.save(new VehicleEntity(null, "Polo", manufacturerEntity1));
    vehicleEntity2 = vehicleRepository.save(new VehicleEntity(null, "Golf", manufacturerEntity1));
    vehicleEntity3 =
        vehicleRepository.save(new VehicleEntity(null, "2er Cabrio", manufacturerEntity2));
    vehicleEntity4 =
        vehicleRepository.save(new VehicleEntity(null, "2er Coupe", manufacturerEntity2));
    vehicleEntity5 =
        vehicleRepository.save(new VehicleEntity(null, "Model S", manufacturerEntity3));
    vehicleEntity6 =
        vehicleRepository.save(new VehicleEntity(null, "Model X", manufacturerEntity3));
  }

  protected VehicleManufacturer convertToDto(VehicleManufacturerEntity entity) {
    return modelMapper.map(entity, VehicleManufacturer.class);
  }

  protected VehicleManufacturerEntity convertToEntity(VehicleManufacturer dto) {
    return modelMapper.map(dto, VehicleManufacturerEntity.class);
  }

  protected Vehicle convertToDto(VehicleEntity entity) {
    return modelMapper.map(entity, Vehicle.class);
  }

  protected List<Vehicle> convertToDto(final List<VehicleEntity> entities) {
    return entities.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  protected VehicleEntity convertToEntity(Vehicle dto) {
    return modelMapper.map(dto, VehicleEntity.class);
  }

  protected List<VehicleEntity> convertToEntity(final List<Vehicle> entities) {
    return entities.stream().map(this::convertToEntity).collect(Collectors.toList());
  }
}
