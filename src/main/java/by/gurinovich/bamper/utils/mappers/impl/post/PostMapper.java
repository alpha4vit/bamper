package by.gurinovich.bamper.utils.mappers.impl.post;

import by.gurinovich.bamper.DTO.post.PostDTO;
import by.gurinovich.bamper.models.postsEntities.Post;
import by.gurinovich.bamper.services.post.PostService;
import by.gurinovich.bamper.services.user.UserService;
import by.gurinovich.bamper.utils.mappers.Mappable;
import by.gurinovich.bamper.utils.mappers.impl.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMapper implements Mappable<Post, PostDTO> {

    private final ModelMapper modelMapper;
    private final UserMapper userMapper;


    @Override
    public Post fromDTO(PostDTO dto) {
        return modelMapper.map(dto, Post.class);
    }

    @Override
    public PostDTO toDTO(Post entity) {
        PostDTO post = modelMapper.map(entity, PostDTO.class);
        post.setUser(userMapper.toDTO(entity.getUser()));
        return post;
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
