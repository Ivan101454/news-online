package ru.clevertec.newsonline.newService.service.interfaces;

import ru.clevertec.newsonline.newService.dto.PictureDto;

public interface PicturePersistencePort {

    PictureDto save(PictureDto pictureDto);
}
