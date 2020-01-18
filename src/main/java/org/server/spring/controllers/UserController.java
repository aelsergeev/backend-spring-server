package org.server.spring.controllers;

import org.server.spring.models.User;
import org.server.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public List<User> getUsers(User user) {
        return userService.getUsers(user);
    }

    @GetMapping("/list")
    public List<User> listUsers() {
        return userService.list();
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.update(user);
    }

    @PutMapping("/img/update")
    public UUID updateUserImage(Long id, MultipartFile image) {
        return userService.updateImage(id, image);
    }

    @GetMapping(value = "/img/{uuid}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getUserImage(@PathVariable UUID uuid) {
        return userService.getImage(uuid);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody User user) {
        userService.delete(user);
    }

}

