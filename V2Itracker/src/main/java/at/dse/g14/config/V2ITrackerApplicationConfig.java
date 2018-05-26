package at.dse.g14.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class V2ITrackerApplicationConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
