package org.server.spring.services;

import org.server.spring.dao.InternLogDao;
import org.server.spring.models.InternLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface InternLogService {
    List<InternLog> list();
    InternLog update(InternLog internLog);
}

@Service
class InternLogServiceImpl implements InternLogService {

    private final InternLogDao internLogDao;

    @Autowired
    public InternLogServiceImpl(InternLogDao internLogDao) {
        this.internLogDao = internLogDao;
    }

    public List<InternLog> list() {
        return  internLogDao.list();
    }

    public InternLog update(InternLog internLog) {
        return internLogDao.update(internLog);
    }

}
