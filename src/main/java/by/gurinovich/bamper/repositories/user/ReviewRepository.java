package by.gurinovich.bamper.repositories.user;

import by.gurinovich.bamper.models.user.Review;
import by.gurinovich.bamper.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUser(User user);
    List<Review> findByAuthor(User user);

}
