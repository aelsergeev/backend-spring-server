package ru.server.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.server.spring.models.Permission;
import ru.server.spring.services.PermissionService;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/list")
    public List<Permission> listPermission() {
        return permissionService.list();
    }

    @PutMapping("/update")
    public Permission updatePermission(@RequestBody Permission permission) {
        return permissionService.update(permission);
    }

    @DeleteMapping("/delete")
    public void deletePermission(@RequestBody Permission permission) {
        permissionService.delete(permission);
    }

}
