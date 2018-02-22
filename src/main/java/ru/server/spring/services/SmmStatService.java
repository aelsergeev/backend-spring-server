package ru.server.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.server.spring.dao.SmmStatDao;
import ru.server.spring.models.SmmStat;
import ru.server.spring.models.api.Statistic;

import java.util.Date;
import java.util.List;

public interface SmmStatService {
    List<SmmStat> list();
    List<Statistic> stat(Date firstDay, Date secondDay);
    SmmStat update(SmmStat smmStat);
}

@Service
class SmmStatServiceImpl implements SmmStatService {

    private final SmmStatDao smmStatDao;

    @Autowired
    public SmmStatServiceImpl(SmmStatDao smmStatDao) {
        this.smmStatDao = smmStatDao;
    }

    public List<SmmStat> list() {
        return smmStatDao.list();
    }

    public List<Statistic> stat(Date firstDay, Date secondDay) {
        return smmStatDao.stat(firstDay, secondDay);
    }

    public SmmStat update(SmmStat smmStat) {
        return smmStatDao.update(smmStat);
    }

}