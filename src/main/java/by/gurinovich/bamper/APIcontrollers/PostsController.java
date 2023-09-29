package by.gurinovich.bamper.APIcontrollers;

import by.gurinovich.bamper.DTO.post.ImageDTO;
import by.gurinovich.bamper.DTO.post.PostDTO;
import by.gurinovich.bamper.models.postsEntities.Image;
import by.gurinovich.bamper.models.postsEntities.Post;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.requests.PostCreation;
import by.gurinovich.bamper.services.post.PostService;
import by.gurinovich.bamper.services.user.UserService;
import by.gurinovich.bamper.utils.mappers.post.ImageMapper;
import by.gurinovich.bamper.utils.mappers.post.PostMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostsController {
    private final PostService postService;
    private final UserService userService;
    private final PostMapper postMapper;
    private final ImageMapper imageMapper;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllPosts(){
        return new ResponseEntity<>(postMapper.toDTOs(postService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<Object> getPostById(@PathVariable("post_id") Integer post_id){
        return new ResponseEntity<>(postMapper.toDTO(postService.getById(post_id)), HttpStatus.OK);
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<Object> getAllPostsForUser(@PathVariable("user_id") Integer id){
        User user = userService.getById(id);
        return new ResponseEntity<>(postMapper.toDTOs(postService.getByUser(user)), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addNewPostForUser(@RequestBody PostCreation postCreation){
        Post post = postService.save(postCreation);
        return new ResponseEntity<>(postMapper.toDTO(post), HttpStatus.CREATED);
    }

    @PostMapping( "/{post_id}/images")
    public ResponseEntity<Object> uploadImageForPost(@PathVariable("post_id") Integer post_id, @ModelAttribute ImageDTO imageDTO){
        Image image = imageMapper.fromDTO(imageDTO);
        postService.uploadImage(post_id, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}/deleteImage")
    @ResponseStatus(HttpStatus.OK)
    public void deleteImage(@PathVariable("post_id") Integer post_id,
                            @RequestBody String jsonRequest){
        JSONObject jsonObject = new JSONObject(jsonRequest);
        String name = jsonObject.getString("name");
        postService.deleteImage(post_id, name);
    }


}
