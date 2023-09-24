package by.gurinovich.bamper.utils.mappers.impl;

import by.gurinovich.bamper.DTO.post.PostDTO;
import by.gurinovich.bamper.models.postsEntities.Post;
import by.gurinovich.bamper.utils.mappers.Mappable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostMapper implements Mappable<Post, PostDTO> {


    @Override
    public Post fromDTO(PostDTO dto) {
        return null; //TODO
    }

    @Override
    public PostDTO toDTO(Post entity) {
        return null;
    }

    @Override
    public List<PostDTO> toDTOs(List<Post> entities) {
        return null;
    }

    @Override
    public List<Post> toEntities(List<PostDTO> dtos) {
        return null;
    }
}
