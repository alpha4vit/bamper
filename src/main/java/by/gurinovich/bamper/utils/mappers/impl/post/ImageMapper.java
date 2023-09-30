package by.gurinovich.bamper.utils.mappers.impl.post;

import by.gurinovich.bamper.DTO.post.ImageDTO;
import by.gurinovich.bamper.models.postsEntities.Image;
import by.gurinovich.bamper.utils.mappers.Mappable;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.Managed;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ImageMapper implements Mappable<Image, ImageDTO> {
    private final ModelMapper modelMapper;

    @Override
    public Image fromDTO(ImageDTO dto) {
        return modelMapper.map(dto, Image.class);
    }

    @Override
    public ImageDTO toDTO(Image entity) {
        return modelMapper.map(entity, ImageDTO.class);
    }

    @Override
    public List<ImageDTO> toDTOs(List<Image> entities) {
        return entities.stream().map(this::toDTO).toList();
    }

    @Override
    public List<Image> toEntities(List<ImageDTO> dtos) {
        return dtos.stream().map(this::fromDTO).toList();
    }
}
