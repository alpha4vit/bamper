package by.gurinovich.bamper.APIcontrollers;

import by.gurinovich.bamper.DTO.post.ImageDTO;
import by.gurinovich.bamper.DTO.user.AddressDTO;
import by.gurinovich.bamper.models.postsEntities.Image;
import by.gurinovich.bamper.models.postsEntities.Post;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.requests.PostCreation;
import by.gurinovich.bamper.services.post.PostService;
import by.gurinovich.bamper.services.post.AddressService;
import by.gurinovich.bamper.services.user.UserService;
import by.gurinovich.bamper.utils.mappers.impl.post.AddressMapper;
import by.gurinovich.bamper.utils.mappers.impl.post.ImageMapper;
import by.gurinovich.bamper.utils.mappers.impl.post.PostMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "Post controller", description = "Post API")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final PostMapper postMapper;
    private final ImageMapper imageMapper;
    private final AddressMapper addressMapper;
    private final AddressService addressService;

    @Operation(summary = "Get all posts")
    @GetMapping("/all")
    public ResponseEntity<Object> getAllPosts(){
        List<Post> posts = postService.getAll();
        return new ResponseEntity<>(postMapper.toDTOs(posts), HttpStatus.OK);
    }

    @Operation(summary = "Get post by id")
    @GetMapping("/{post_id}")
    public ResponseEntity<Object> getPostById(@PathVariable("post_id") Long postId){
        return new ResponseEntity<>(postMapper.toDTO(postService.getById(postId)), HttpStatus.OK);
    }

    @Operation(summary = "Delete post by id")
    @DeleteMapping("/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@customSecurityExpression.isPostOwner(#postId)")
    public void deletePostId(@PathVariable("post_id") Long postId){
        postService.deleteById(postId);
    }


    @Operation(summary = "Get all posts for user by user id")
    @GetMapping("/users/{user_id}")
    public ResponseEntity<Object> getAllPostsForUser(@PathVariable("user_id") Long userId){
        User user = userService.getById(userId);
        return new ResponseEntity<>(postMapper.toDTOs(postService.getByUser(user)), HttpStatus.OK);
    }

    @Operation(summary = "Add new post")
    @PostMapping("/add")
    public ResponseEntity<Object> addNewPost(@RequestBody PostCreation postCreation){
        Post post = postService.save(postCreation);
        return new ResponseEntity<>(postMapper.toDTO(post), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all images for post by post id")
    @GetMapping("/{post_id}/images")
    public ResponseEntity<Object> getAllImagesForPost(@PathVariable("post_id") Long postId){
        return new ResponseEntity<>(postService.getById(postId).getImages(), HttpStatus.OK);
    }

    @Operation(summary = "Add image for post by post id")
    @PostMapping( "/{post_id}/images/add")
    @PreAuthorize("@customSecurityExpression.isPostOwner(#postId)")
    public ResponseEntity<Object> uploadImageForPost(@PathVariable("post_id") Long postId, @ModelAttribute ImageDTO imageDTO){
        Image image = imageMapper.fromDTO(imageDTO);
        postService.uploadImage(postId, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete image for post by post id")
    @DeleteMapping("/{post_id}/images/delete")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("@customSecurityExpression.isPostOwner(#postId)")
    public void deleteImage(@PathVariable("post_id") Long postId,
                            @RequestBody String jsonRequest){
        JSONObject jsonObject = new JSONObject(jsonRequest);
        String name = jsonObject.getString("name");
        postService.deleteImage(postId, name);
    }

    @Operation(summary = "Get addresses of post")
    @GetMapping("/{post_id}/address")
    public ResponseEntity<Object> getAddressesForPost(@PathVariable("post_id") Long id){
        return new ResponseEntity<>(addressMapper.toDTOs(postService.getById(id).getAddresses()), HttpStatus.OK);
    }


    @Operation(summary = "Add address to the post")
    @PatchMapping("/{post_id}/addresses/add")
    public ResponseEntity<Object> addAddress(@PathVariable("post_id") Long id, @RequestBody AddressDTO addressDTO){
        Post post = postService.getById(id);
        addressService.save(post, addressDTO.getAddress());
        return new ResponseEntity<>(addressService.getAllByPost(post), HttpStatus.OK);
    }

    @Operation(summary = "Delete address of the post")
    @DeleteMapping("/{post_id}/addresses/delete/{address_id}")
    public ResponseEntity<Object> deleteAddressById(@PathVariable("post_id") Long post_id,
                                                    @PathVariable("address_id") Long address_id){
        addressService.deleteById(address_id);
        return new ResponseEntity<>(addressService.getAllByPost(postService.getById(post_id)), HttpStatus.OK);
    }

    @Operation(summary = "Delete all addresses for post")
    @DeleteMapping("/{post_id}/addresses/delete/all")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllAdressesForPost(@PathVariable("post_id") Long post_id){
        addressService.deleteAllByPost(postService.getById(post_id));
    }

}
