package by.gurinovich.bamper.APIcontrollers;

import by.gurinovich.bamper.DTO.user.ReviewDTO;
import by.gurinovich.bamper.models.user.Review;
import by.gurinovich.bamper.services.user.ReviewService;
import by.gurinovich.bamper.services.user.UserService;
import by.gurinovich.bamper.utils.mappers.impl.user.ReviewMapper;
import by.gurinovich.bamper.utils.mappers.impl.user.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User controller", description = "User API")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    private final UserMapper userMapper;

    @GetMapping("/all")
    @Operation(summary = "Get all users")
    public ResponseEntity<Object> getAllUsers(){
        return new ResponseEntity<>(userMapper.toDTOs(userService.getAll()), HttpStatus.OK);
    }

    @Operation(summary = "Get user by id")
    @GetMapping("/{user_id}")
    public ResponseEntity<Object> getUserById(@PathVariable("user_id") Long id){
        return new ResponseEntity<>(userMapper.toDTO(userService.getById(id)), HttpStatus.OK);
    }

    @Operation(summary = "Get reviews of user by its id")
    @GetMapping("/{user_id}/reviewsFor")
    public ResponseEntity<Object> getReviewsForTheUser(@PathVariable("user_id") Long userId){
        return new ResponseEntity<>(reviewMapper.toDTOs(reviewService.getAllByUser(userId)), HttpStatus.OK);
    }

    @Operation(summary = "Get reviews created by user with this id")
    @GetMapping("/{user_id}/reviewsBy")
    public ResponseEntity<Object> getReviewsCreatedByThisUser(@PathVariable("user_id") Long userId){
        return new ResponseEntity<>(reviewMapper.toDTOs(reviewService.getAllByAuthor(userId)), HttpStatus.OK);
    }

    @Operation(summary = "Get review by id")
    @GetMapping("/reviews/{review_id}")
    public ResponseEntity<Object> getReviewById(@PathVariable("review_id") Long reviewId){
        return new ResponseEntity<>(reviewMapper.toDTO(reviewService.getById(reviewId)), HttpStatus.OK);
    }

    @Operation(summary = "Delete review by id")
    @DeleteMapping("/reviews/{review_id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReviewById(@PathVariable("review_id") Long reviewId){
        reviewService.deleteById(reviewId);
    }

    @Operation(summary = "Add review for user")
    @PostMapping("/{user_id}/reviews/add")
    public ResponseEntity<Object> createNewReview(@PathVariable("user_id") Long userId,
                                                  @Valid @RequestBody ReviewDTO reviewDTO){
        Review created = reviewService.save(userId, reviewDTO);
        return new ResponseEntity<>(reviewMapper.toDTO(created), HttpStatus.CREATED);
    }


}
