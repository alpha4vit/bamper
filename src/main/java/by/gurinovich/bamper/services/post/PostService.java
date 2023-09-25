package by.gurinovich.bamper.services.post;

import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.postsEntities.Image;
import by.gurinovich.bamper.models.postsEntities.Post;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.repositories.posts.PostRepo;
import by.gurinovich.bamper.security.JWTEntity;
import by.gurinovich.bamper.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo postRepo;
    private final UserService userService;
    private final ImageService imageService;

    public List<Post> getAll(){
        return postRepo.findAll();
    }

    public Post getById(Integer id){
        Optional<Post> post = postRepo.findById(id);
        if (post.isEmpty())
            throw new ResourceNotFoundException("Post with this id already exists");
        return post.get();
    }

    public List<Post> getByUser(User user){
        return postRepo.findByUser(user);
    }

    public Post save(Post post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTEntity jwtEntity = (JWTEntity) authentication.getPrincipal();

        post.setUser(userService.getById(jwtEntity.getId()));

        return postRepo.save(post);
    }

    @Transactional
    public void uploadImage(Integer id, Image image) {
        Post post = getById(id);
        String imageName = imageService.upload(image);
    }
}
