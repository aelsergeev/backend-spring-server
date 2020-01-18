package org.server.spring.services;

import org.server.spring.dao.ModStatDao;
import org.server.spring.models.ModStat;
import org.server.spring.models.api.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface ModStatService {
    ModStat update(ModStat modStat);
    void clear();
    List<ModStat> list();
    List<Statistic> stat(String category, Date firstDay, Date secondDay);
}

@Service
class ModStatServiceImpl implements ModStatService {

    private final ModStatDao modStatDao;

    @Autowired
    public ModStatServiceImpl(ModStatDao modStatDao) {
        this.modStatDao = modStatDao;
    }

    public ModStat update(ModStat modStat) {
        return modStatDao.update(modStat);
    }

    public void clear() {
        Date date = new Date();
        Date date30DaysAgo = new Date(date.getTime() - (long) 2.592e+9);

        modStatDao.clear(date30DaysAgo);
    }

    public List<ModStat> list() {
        return modStatDao.list();
    }

    public List<Statistic> stat(String category, Date firstDay, Date secondDay) {
        return modStatDao.stat(category, firstDay, secondDay);
    }

}

