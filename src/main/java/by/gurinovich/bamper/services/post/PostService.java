package by.gurinovich.bamper.services.post;

import by.gurinovich.bamper.models.postsEntities.Post;
import by.gurinovich.bamper.repositories.posts.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;

    public List<Post> findAll(){
        return postRepo.findAll();
    }

    public Post findById(Integer id){
        return postRepo.findById(id).orElse(null);
    }
}
