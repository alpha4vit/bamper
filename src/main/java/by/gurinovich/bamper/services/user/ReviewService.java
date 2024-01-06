package by.gurinovich.bamper.services.user;

import by.gurinovich.bamper.DTO.user.ReviewDTO;
import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.user.Review;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.repositories.user.ReviewRepository;
import by.gurinovich.bamper.utils.mappers.impl.user.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ReviewMapper reviewMapper;

    public Review getById(Long id){
        return reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review with this id not found!"));
    }

    public List<Review> getAllByUser(Long userId){
        User user = userService.getById(userId);
        return reviewRepository.findByUser(user);
    }

    public List<Review> getAllByAuthor(Long userId){
        User user = userService.getById(userId);
        return reviewRepository.findByAuthor(user);
    }

    @Transactional
    public Review save(Long userId, ReviewDTO reviewDTO){
        User user = userService.getById(userId);
        User author = userService.getAuthorizedUser();
        if (user.getId().equals(author.getId()))
            throw new InvalidOperationException("User cant create review for himself");
        Review review = reviewMapper.fromDTO(reviewDTO);
        review.setUser(user);
        review.setAuthor(author);
        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteById(Long id){
        if (reviewRepository.findById(id).isEmpty())
            throw new ResourceNotFoundException("Review with this id not found!");
        reviewRepository.deleteById(id);
    }
}
