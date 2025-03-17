package ru.clevertec.newsonline.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class SaveImage {

    public String persist(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            String uploadDir = "c:/uploads/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            String filePath = uploadDir + image.getOriginalFilename();
            File destinationFile = new File(filePath);
            try {
                image.transferTo(destinationFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return filePath;
        } else {
            throw new RuntimeException("Нет изображения");
        }
    }
}
