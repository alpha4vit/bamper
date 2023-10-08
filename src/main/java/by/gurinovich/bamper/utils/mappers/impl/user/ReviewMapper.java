package by.gurinovich.bamper.utils.mappers.impl.user;

import by.gurinovich.bamper.DTO.user.ReviewDTO;
import by.gurinovich.bamper.models.user.Review;
import by.gurinovich.bamper.utils.mappers.Mappable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewMapper implements Mappable<Review, ReviewDTO> {
    private final ModelMapper modelMapper;

    @Override
    public Review fromDTO(ReviewDTO dto) {
        return modelMapper.map(dto, Review.class);
    }

    @Override
    public ReviewDTO toDTO(Review entity) {
        return modelMapper.map(entity, ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> toDTOs(List<Review> entities) {
        return entities.stream().map(this::toDTO).toList();
    }

    @SneakyThrows
    @Override
    public List<Review> toEntities(List<ReviewDTO> dtos) {
        return dtos.stream().map(this::fromDTO).toList();
    }
}
