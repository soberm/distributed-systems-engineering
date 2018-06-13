package at.dse.g14.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import at.dse.g14.commons.dto.data.Vehicle;
import org.junit.Test;
import org.springframework.http.MediaType;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
public class VehicleControllerTest extends AbstractControllerTest {

  @Test
  public void createVehicle() throws Exception {
    mockMvc
        .perform(
            post("/manufacturer/{id}/vehicle", String.valueOf(manufacturerEntity1.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gson.toJson(new Vehicle(null, "Polo", convertToDto(manufacturerEntity1)))))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  public void getVehicle() throws Exception {
    // TODO
  }

  @Test
  public void getAllVehicleOfManufacturer() throws Exception {
    // TODO
  }

  @Test
  public void updateVehicle() throws Exception {
    // TODO
  }
}
