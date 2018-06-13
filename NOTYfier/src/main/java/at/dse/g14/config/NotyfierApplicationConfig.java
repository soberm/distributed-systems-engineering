package at.dse.g14.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * General configuration of the application.
 *
 * @author Michael Sober
 * @since 1.0
 */
@Configuration
public class NotyfierApplicationConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
