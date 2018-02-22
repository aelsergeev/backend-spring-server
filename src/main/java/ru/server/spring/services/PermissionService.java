package ru.server.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.server.spring.dao.PermissionDao;
import ru.server.spring.models.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> list();
    Permission update(Permission permission);
    void delete(Permission permission);
}

@Service
class PermissionServiceImpl implements PermissionService {

    private final PermissionDao permissionDao;

    @Autowired
    public PermissionServiceImpl(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    public List<Permission> list() {
        return permissionDao.list();
    }

    public Permission update(Permission permission) {
        return permissionDao.update(permission);
    }

    public void delete(Permission permission) {
        permissionDao.delete(permission);
    }

}