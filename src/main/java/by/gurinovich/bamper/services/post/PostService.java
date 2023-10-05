package by.gurinovich.bamper.services.post;

import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.postsEntities.Image;
import by.gurinovich.bamper.models.postsEntities.Post;
import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.repositories.posts.PostRepo;
import by.gurinovich.bamper.requests.PostCreation;
import by.gurinovich.bamper.security.JWTEntity;
import by.gurinovich.bamper.services.sparePart.SparePartService;
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
    private final SparePartService sparePartService;

    public List<Post> getAll(){
        return postRepo.findAll();
    }

    public Post getById(Integer id){
        return postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with this id not found"));
    }

    public List<Post> getByUser(User user){
        return postRepo.findByUser(user);
    }

    public Post save(PostCreation postCreation) {
        SparePart sparePart = sparePartService.getById(postCreation.getSparePartId());
        Post post = new Post(postCreation.getTitle(), userService.getAuthorizedUser(), sparePart);
        return postRepo.save(post);
    }

    @Transactional
    public void uploadImage(Integer id, Image image) {
        Post post = getById(id);
        String imageName = imageService.upload(image);
        List<String> images = post.getImages();
        images.add(imageName);
        post.setImages(images);
        postRepo.save(post);
    }

    @Transactional
    public void deleteImage(Integer post_id, String imageName){
        Post post = getById(post_id);
        List<String> images = post.getImages();
        if (!images.remove(imageName))
            throw new ResourceNotFoundException("Image with this name not found for this post");
        imageService.removeImage(imageName);
        postRepo.save(post);
    }

    @Transactional
    public void deleteById(Integer id){
        if (postRepo.findById(id).isEmpty())
            throw new ResourceNotFoundException("Post with this id not found");
        postRepo.deleteById(id);
    }

}
