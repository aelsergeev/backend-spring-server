package ru.server.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.server.spring.configs.ImageConfiguration;
import ru.server.spring.dao.ImageDao;
import ru.server.spring.dao.UserDao;
import ru.server.spring.models.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> list();
    List<User> getUsers(User user);
    User getUserById(Long id);
    User getUserByUsername(String username);
    User update(User user);
    UUID updateImage(Long userId, MultipartFile image);
    List<User> getUsersWithNullAdmUserId();
    byte[] getImage(UUID uuid);
    void delete(User user);
}

@Service
class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ImageDao imageDao;
    private final ImageConfiguration imageConfiguration;
    private final SessionService sessionService;

    @Autowired
    public UserServiceImpl(UserDao userDao, ImageDao imageDao, ImageConfiguration imageConfiguration, SessionService sessionService) {
        this.userDao = userDao;
        this.imageDao = imageDao;
        this.imageConfiguration = imageConfiguration;
        this.sessionService = sessionService;
    }

    public List<User> getUsers(User user) {
        return userDao.getUsers(user);
    }

    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public List<User> list() {
        return userDao.list();
    }

    public User update(User user) {
        User newUser = userDao.update(user);

        sessionService.expireUserSessions(user.getUsername());

        return newUser;
    }

    public UUID updateImage(Long userId, MultipartFile image) {
        String directory = imageConfiguration.getImageProperties().getEmployee();
        User user = userDao.getUserById(userId);
        UUID previousImageUuid = user.getAvatar();

        if (previousImageUuid != null) imageDao.deleteImage(previousImageUuid, directory);

        UUID imageUuid = UUID.randomUUID();

        imageDao.saveImage(imageUuid, directory, image);

        user.setAvatar(imageUuid);

        User newUser = userDao.update(user);

        sessionService.expireUserSessions(user.getUsername());

        return newUser.getAvatar();
    }

    @Override
    public List<User> getUsersWithNullAdmUserId() {
        return userDao.getUsersWithNullAdmUserId();
    }

    public byte[] getImage(UUID uuid) {
        Path imagePath = Paths.get(imageConfiguration.getImageProperties().getEmployee(), uuid.toString().replace("-","/") + ".jpg");

        BufferedImage bufferedImage = imageDao.getImage(imagePath);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

    public void delete(User user) { userDao.delete(user); }

}
