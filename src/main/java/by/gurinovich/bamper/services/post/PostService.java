package by.gurinovich.bamper.services.post;

import by.gurinovich.bamper.models.postsEntities.Post;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.repositories.posts.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;

    public List<Post> getAll(){
        return postRepo.findAll();
    }

    public Post getById(Integer id){
        return postRepo.findById(id).orElse(null);
    }

    public List<Post> getByUser(User user){
        return postRepo.findByUser(user);
    }
}
