package ru.server.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Repository
@Transactional
public class ImageDao {

    public BufferedImage getImage(Path path) {
        try {
            return ImageIO.read(path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteImage(UUID uuid, String directory) {
        Path path = Paths.get(directory, uuid.toString().replace("-","/") + ".jpg");
        return path.toFile().delete();
    }

    public boolean saveImage(UUID uuid, String directory, MultipartFile image) {
        boolean isCurrentImageDirExist = false;

        Path path = Paths.get(directory, uuid.toString().replace("-","/") + ".jpg");
        File absoluteFilePath = path.getParent().toFile();

        if (!absoluteFilePath.exists()) isCurrentImageDirExist = absoluteFilePath.mkdirs();

        if (isCurrentImageDirExist) {
            try {
                BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
                return ImageIO.write(bufferedImage, "jpg", path.toFile());
            } catch (IOException e) {
                e.printStackTrace();

                return false;
            }
        } else {
            return false;
        }
    }

}
