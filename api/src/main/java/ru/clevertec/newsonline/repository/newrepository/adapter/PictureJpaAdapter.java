package ru.clevertec.newsonline.repository.newrepository.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.entity.Picture;
import ru.clevertec.newsonline.mapper.NewsMapper;
import ru.clevertec.newsonline.newService.dto.PictureDto;
import ru.clevertec.newsonline.newService.service.interfaces.PicturePersistencePort;
import ru.clevertec.newsonline.repository.newrepository.PictureRepository;

@Repository
@RequiredArgsConstructor
public class PictureJpaAdapter implements PicturePersistencePort {

    private final PictureRepository pictureRepository;
    private final NewsMapper newsMapper;

    public PictureDto save(PictureDto pictureDto) {
        Picture save = pictureRepository.save(newsMapper.pictureDtoToPicture(pictureDto));
        return newsMapper.pictureToPictureDto(save);
    }
}
