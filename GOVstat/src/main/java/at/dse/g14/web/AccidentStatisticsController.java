package at.dse.g14.web;

import at.dse.g14.commons.dto.AccidentStatisticsDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.AccidentStatistics;
import at.dse.g14.service.IAccidentStatisticsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A Rest-Controller, which provides the necessary endpoints to retrieve AccidentStatistics.
 *
 * @author Michael Sober
 * @since 1.0
 * @see CrudRepository
 */
@RestController
@RequestMapping("/accident-statistics")
public class AccidentStatisticsController {

  private final ModelMapper modelMapper;
  private final IAccidentStatisticsService accidentStatisticsService;

  @Autowired
  public AccidentStatisticsController(
      ModelMapper modelMapper, IAccidentStatisticsService accidentStatisticsService) {
    this.modelMapper = modelMapper;
    this.accidentStatisticsService = accidentStatisticsService;
  }

  /**
   * Finds all AccidentStatistics.
   *
   * @return all AccidentStatistics, which are currently available in the system.
   * @throws ServiceException if an error, in the services occur.
   */
  @GetMapping
  public List<AccidentStatisticsDTO> getAccidentStatistics() throws ServiceException {
    return convertToDto(accidentStatisticsService.findAll());
  }

  /**
   * Converts an AccidentStatistics entity to a dto.
   *
   * @param entity which should get converted to a dto.
   * @return the dto, which got created from mapping the entity.
   */
  public AccidentStatisticsDTO convertToDto(final AccidentStatistics entity) {
    return modelMapper.map(entity, AccidentStatisticsDTO.class);
  }

  /**
   * Converts a list of AccidentStatistics entities to a list of dtos.
   *
   * @param entities which should get converted to dtos.
   * @return the dtos, which got created from mapping the entities.
   */
  public List<AccidentStatisticsDTO> convertToDto(final List<AccidentStatistics> entities) {
    return entities.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}
