package at.dse.g14.web;

import at.dse.g14.commons.dto.AccidentStatisticsDTO;
import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.AccidentStatistics;
import at.dse.g14.service.IAccidentStatisticsService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @GetMapping
  public List<AccidentStatisticsDTO> getAccidentStatistics() throws ServiceException {
    return convertToDto(accidentStatisticsService.findAll());
  }

  public AccidentStatisticsDTO convertToDto(final AccidentStatistics entity) {
    return modelMapper.map(entity, AccidentStatisticsDTO.class);
  }

  public List<AccidentStatisticsDTO> convertToDto(final List<AccidentStatistics> entities) {
    return entities.stream().map(this::convertToDto).collect(Collectors.toList());
  }
}
