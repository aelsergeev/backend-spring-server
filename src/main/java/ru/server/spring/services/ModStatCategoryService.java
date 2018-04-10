package ru.server.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.server.spring.dao.ModStatCategoryDao;
import ru.server.spring.models.ModStatCategory;

import java.time.Instant;
import java.util.UUID;

public interface ModStatCategoryService {
    void update(String name, int count);
}

@Service
class ModStatCategoryServiceImpl implements ModStatCategoryService {

    private final ModStatCategoryDao modStatCategoryDao;

    @Autowired
    public ModStatCategoryServiceImpl(ModStatCategoryDao modStatCategoryDao) {
        this.modStatCategoryDao = modStatCategoryDao;
    }

    public void update(String name, int count) {
        UUID uuid = UUID.randomUUID();
        Instant instant = Instant.now();

        ModStatCategory modStatCategory = new ModStatCategory();
        modStatCategory.setCount(count);
        modStatCategory.setName(name);
        modStatCategory.setId(uuid);
        modStatCategory.setDate(instant);

        modStatCategoryDao.update(modStatCategory);
    }

}