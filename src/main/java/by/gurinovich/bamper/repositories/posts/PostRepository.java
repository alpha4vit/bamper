package by.gurinovich.bamper.repositories.posts;

import by.gurinovich.bamper.models.postsEntities.Post;
import by.gurinovich.bamper.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
}
