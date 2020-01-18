package org.server.spring.dao;

import lombok.Data;
import org.server.spring.models.api.HelpdeskQueueApi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Data
public class CachedDao {

    private List<HelpdeskQueueApi> groupHelpDeskCount;

}
