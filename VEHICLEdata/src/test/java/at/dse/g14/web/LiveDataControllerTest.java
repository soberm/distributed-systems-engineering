package at.dse.g14.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import at.dse.g14.commons.dto.GpsPoint;
import at.dse.g14.commons.dto.LiveData;
import org.junit.Test;
import org.springframework.http.MediaType;

/**
 * @author Lukas Baronyai
 * @version ${buildVersion}
 * @since 1.0.0
 */
public class LiveDataControllerTest extends AbstractControllerTest {

  @Test
  public void createLiveData_ShouldReturn() throws Exception {
    mockMvc
        .perform(
            post(
                "/manufacturer/{id}/vehicle/{vid}/livedata",
                String.valueOf(manufacturerEntity1.getId()),
                String.valueOf(vehicleEntity1.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(
                    gson.toJson(
                        new LiveData(
                            null,
                            0,
                            new GpsPoint(5, 5),
                            50,
                            10,
                            10,
                            false,
                            false,
                            convertToDto(vehicleEntity1)))))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  public void getVehicleData() throws Exception {
    // TODO
  }
}
