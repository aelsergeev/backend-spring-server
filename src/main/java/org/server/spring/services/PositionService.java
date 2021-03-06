package org.server.spring.services;

import org.server.spring.dao.PositionDao;
import org.server.spring.models.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PositionService {
    List<Position> list();
    Position update(Position position);
    void delete(Position position);
}

@Service
class PositionServiceImpl implements PositionService {

    private final PositionDao positionDao;

    @Autowired
    public PositionServiceImpl(PositionDao positionDao) {
        this.positionDao = positionDao;
    }

    public List<Position> list() {
        return positionDao.list();
    }

    public Position update(Position position) {
        return positionDao.update(position);
    }

    public void delete(Position position) {
        positionDao.delete(position);
    }

}
