package by.gurinovich.bamper.utils.mappers.post;

import by.gurinovich.bamper.DTO.post.PostDTO;
import by.gurinovich.bamper.models.postsEntities.Post;
import by.gurinovich.bamper.utils.mappers.Mappable;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMapper implements Mappable<Post, PostDTO> {

    private final ModelMapper modelMapper;


    @Override
    public Post fromDTO(PostDTO dto) {
        return modelMapper.map(dto, Post.class);
    }

    @Override
    public PostDTO toDTO(Post entity) {
        return modelMapper.map(entity, PostDTO.class);
    }

    @Override
    public List<PostDTO> toDTOs(List<Post> entities) {
        return entities.stream().map(this::toDTO).toList();
    }

    @Override
    public List<Post> toEntities(List<PostDTO> dtos) {
        return dtos.stream().map(this::fromDTO).toList();
    }
}
