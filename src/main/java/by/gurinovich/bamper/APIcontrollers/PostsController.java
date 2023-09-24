package by.gurinovich.bamper.APIcontrollers;

import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.services.post.PostService;
import by.gurinovich.bamper.services.user.UserService;
import by.gurinovich.bamper.utils.mappers.impl.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostsController {
    private final PostService postService;
    private final UserService userService;
    private final PostMapper postMapper;

    @GetMapping("/all")
    public ResponseEntity<Object> getAllPosts(){
        return new ResponseEntity<>(postMapper.toDTOs(postService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<Object> getAllPostsForUser(@PathVariable("user_id") Integer id){
        User user = userService.getById(id);
        return new ResponseEntity<>(postService.getByUser(user), HttpStatus.OK);
    }
}
