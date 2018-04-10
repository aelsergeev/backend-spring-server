package ru.server.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.server.spring.dao.TrafficDao;
import ru.server.spring.models.TrafficTemplate;

import java.util.List;

public interface TrafficService {
    List<TrafficTemplate> listTemplates();
    TrafficTemplate updateTemplates(TrafficTemplate trafficTemplate);
    void deleteTemplates(TrafficTemplate trafficTemplate);
}

@Service
class TrafficTemplatesServiceImpl implements TrafficService {

    private final TrafficDao trafficDao;

    @Autowired
    public TrafficTemplatesServiceImpl(TrafficDao trafficDao) {
        this.trafficDao = trafficDao;
    }

    public List<TrafficTemplate> listTemplates() {
        return trafficDao.listTemplates();
    }

    public TrafficTemplate updateTemplates(TrafficTemplate trafficTemplate) {
        return trafficDao.updateTemplates(trafficTemplate);
    }

    public void deleteTemplates(TrafficTemplate trafficTemplate) {
        trafficDao.deleteTemplates(trafficTemplate);
    }

}
