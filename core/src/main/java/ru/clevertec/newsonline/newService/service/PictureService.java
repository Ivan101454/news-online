package ru.clevertec.newsonline.newService.service;

import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.newService.dto.PictureDto;
import ru.clevertec.newsonline.newService.service.interfaces.PicturePersistencePort;
import ru.clevertec.newsonline.newService.service.interfaces.PictureServicePort;

import java.util.Optional;

@Transactional
public class PictureService implements PictureServicePort {

    private final PicturePersistencePort picturePersistencePort;

    public PictureService(PicturePersistencePort picturePersistencePort) {
        this.picturePersistencePort = picturePersistencePort;
    }

    @Override
    public Optional<PictureDto> create(PictureDto pictureDto) {
        return Optional.of(picturePersistencePort.save(pictureDto));
    }
}
