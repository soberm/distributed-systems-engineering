package at.dse.g14;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Primary
@Component
public class SwaggerResourceProviderImpl implements SwaggerResourcesProvider {

  @Value("${notyfier.service}")
  private String notyfierService;

  @Value("${vehicledata.service}")
  private String vehicledataService;

  @Value("${v2itracker.service}")
  private String v2itrackerService;

  @Override
  public List<SwaggerResource> get() {
    List<SwaggerResource> resources = new ArrayList<>();
    resources.add(swaggerResource("notyfier-service", "/" + notyfierService + "/v2/api-docs", "2.0"));
    resources.add(swaggerResource("vehicledata-service", "/" + vehicledataService + "/v2/api-docs", "2.0"));
    resources.add(swaggerResource("v2itracker-service", "/" + v2itrackerService + "/v2/api-docs", "2.0"));
    return resources;
  }

  private SwaggerResource swaggerResource(String name, String location, String version) {
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(location);
    swaggerResource.setSwaggerVersion(version);
    return swaggerResource;
  }
}
