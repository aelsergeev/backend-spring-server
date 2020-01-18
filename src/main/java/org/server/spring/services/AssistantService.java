package org.server.spring.services;

import org.server.spring.dao.AssistantDao;
import org.server.spring.models.assistant.AssistantGraphVertex;
import org.server.spring.models.assistant.AssistantGraphs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AssistantService {
    List<AssistantGraphVertex> list();
    List<AssistantGraphVertex> graphInfoListByGraphUuid(UUID graph);
    AssistantGraphVertex graphInfoHeadByGraphUuid(UUID graph);
    AssistantGraphVertex graphInfoByUuid(UUID uuid);
    List<AssistantGraphVertex> graphInfoListByUuid(Set<UUID> uuidSet);
    List<AssistantGraphs> graphsList();
    AssistantGraphVertex update(AssistantGraphVertex assistantGraphVertex);
    AssistantGraphs update(AssistantGraphs assistantGraphs);
    void delete(AssistantGraphVertex assistantGraphVertex);
    void delete(AssistantGraphs assistantGraphs);
}

@Service
class AssistantServiceImpl implements AssistantService {

    private final AssistantDao assistantDao;

    @Autowired
    AssistantServiceImpl(AssistantDao assistantDao) {
        this.assistantDao = assistantDao;
    }

    public List<AssistantGraphVertex> list() {
        return assistantDao.list();
    }

    public List<AssistantGraphVertex> graphInfoListByGraphUuid(UUID graph) {
        return assistantDao.graphInfoListByGraphUuid(graph);
    }

    public AssistantGraphVertex graphInfoHeadByGraphUuid(UUID graph) {
        return assistantDao.graphInfoHeadByGraphUuid(graph);
    }


    public AssistantGraphVertex graphInfoByUuid(UUID uuid) {
        return assistantDao.graphInfoByUuid(uuid);
    }

    public List<AssistantGraphVertex> graphInfoListByUuid(Set<UUID> uuidSet) {
        List<AssistantGraphVertex> list = new ArrayList<>();

        for (UUID uuid : uuidSet) list.add(assistantDao.graphInfoByUuid(uuid));

        return list;
    }

    public AssistantGraphVertex update(AssistantGraphVertex assistantGraphVertex) {
        return assistantDao.update(assistantGraphVertex);
    }

    public void delete(AssistantGraphVertex assistantGraphVertex) {
        assistantDao.delete(assistantGraphVertex);
    }

    public List<AssistantGraphs> graphsList() {
        return assistantDao.graphsList();
    }

    public AssistantGraphs update(AssistantGraphs assistantGraphs) {
        return assistantDao.update(assistantGraphs);
    }

    public void delete(AssistantGraphs assistantGraphs) {
        assistantDao.delete(assistantGraphs);
    }

}
