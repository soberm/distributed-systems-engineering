package at.dse.g14.service.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import at.dse.g14.service.IVehicleTrackService;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VehicleTrackServiceTest {

  @Autowired
  private IVehicleTrackService vehicleTrackService;

  @Test
  public void save_validVehicleTrack_shouldPersist(){
    //TODO
  }

  @Test
  public void update_validVehicleTrack_shouldPersist(){
    //TODO
  }

  @Test
  public void delete_validVehicleTrack_shouldPersist(){
    //TODO
  }

  @Test
  public void findOne_emptyDatabase_shouldReturnNull(){
    assertThat(vehicleTrackService.findOne(0L), is(nullValue()));
  }

  @Test
  public void findAll_emptyDatabase_shouldReturnEmptyList() {
    assertThat(vehicleTrackService.findAll(), IsEmptyCollection.empty());
  }

}
