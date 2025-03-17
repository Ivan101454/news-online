package ru.clevertec.newsonline.newService.service.interfaces;

import ru.clevertec.newsonline.newService.dto.PictureDto;

import java.util.Optional;

public interface PictureServicePort {

    Optional<PictureDto> create(PictureDto pictureDto);
}
