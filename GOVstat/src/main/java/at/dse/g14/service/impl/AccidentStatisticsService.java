package at.dse.g14.service.impl;

import at.dse.g14.commons.service.exception.ServiceException;
import at.dse.g14.entity.AccidentStatistics;
import at.dse.g14.service.IAccidentStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AccidentStatisticsService implements IAccidentStatisticsService {
    @Override
    public AccidentStatistics save(AccidentStatistics entity) throws ServiceException {
        return null;
    }

    @Override
    public AccidentStatistics update(AccidentStatistics entity) throws ServiceException {
        return null;
    }

    @Override
    public void delete(Long aLong) throws ServiceException {

    }

    @Override
    public AccidentStatistics findOne(Long aLong) throws ServiceException {
        return null;
    }

    @Override
    public List<AccidentStatistics> findAll() throws ServiceException {
        return null;
    }
}
