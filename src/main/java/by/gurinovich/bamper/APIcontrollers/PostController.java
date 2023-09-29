package by.gurinovich.bamper.APIcontrollers;

import by.gurinovich.bamper.DTO.post.ImageDTO;
import by.gurinovich.bamper.models.postsEntities.Image;
import by.gurinovich.bamper.models.postsEntities.Post;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.requests.PostCreation;
import by.gurinovich.bamper.services.post.PostService;
import by.gurinovich.bamper.services.user.UserService;
import by.gurinovich.bamper.utils.mappers.post.ImageMapper;
import by.gurinovich.bamper.utils.mappers.post.PostMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final PostMapper postMapper;
    private final ImageMapper imageMapper;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllPosts(){
        return new ResponseEntity<>(postMapper.toDTOs(postService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<Object> getPostById(@PathVariable("post_id") Integer postId){
        return new ResponseEntity<>(postMapper.toDTO(postService.getById(postId)), HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@customSecurityExpression.isPostOwner(#postId)")
    public void deletePostId(@PathVariable("post_id") Integer postId){
        postService.deleteById(postId);
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<Object> getAllPostsForUser(@PathVariable("user_id") Integer userId){
        User user = userService.getById(userId);
        return new ResponseEntity<>(postMapper.toDTOs(postService.getByUser(user)), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addNewPost(@RequestBody PostCreation postCreation){
        Post post = postService.save(postCreation);
        return new ResponseEntity<>(postMapper.toDTO(post), HttpStatus.CREATED);
    }

    @GetMapping("/{post_id}/images")
    public ResponseEntity<Object> getAllImagesForPost(@PathVariable("post_id") Integer postId){
        return new ResponseEntity<>(postService.getById(postId).getImages(), HttpStatus.OK);
    }

    @PostMapping( "/{post_id}/images/add")
    @PreAuthorize("@customSecurityExpression.isPostOwner(#postId)")
    public ResponseEntity<Object> uploadImageForPost(@PathVariable("post_id") Integer postId, @ModelAttribute ImageDTO imageDTO){
        Image image = imageMapper.fromDTO(imageDTO);
        postService.uploadImage(postId, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}/images/delete")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@customSecurityExpression.isPostOwner(#postId)")
    public void deleteImage(@PathVariable("post_id") Integer postId,
                            @RequestBody String jsonRequest){
        JSONObject jsonObject = new JSONObject(jsonRequest);
        String name = jsonObject.getString("name");
        postService.deleteImage(postId, name);
    }


}
