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

    String persist(MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            String uploadDir = "uploads/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String filePath = uploadDir + image.getOriginalFilename();
            File destinationFile = new File(filePath);
            image.transferTo(destinationFile);
            System.out.println("Файл загружен: " + filePath);
            return filePath;
        } else {
            throw new RuntimeException("Нет изображения");
        }
    }
}
