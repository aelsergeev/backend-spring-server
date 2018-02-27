package ru.server.spring.dao;

import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.server.spring.models.api.HelpdeskQueueCount;

import java.util.List;

@Repository
@Data
public class CachedDao {

    private List<HelpdeskQueueCount> groupHelpDeskCount;

}
