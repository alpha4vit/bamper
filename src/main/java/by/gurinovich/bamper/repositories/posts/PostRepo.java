package by.gurinovich.bamper.repositories.posts;

import by.gurinovich.bamper.models.postsEntities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
}
