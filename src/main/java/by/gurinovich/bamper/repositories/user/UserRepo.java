package by.gurinovich.bamper.repositories.user;

import by.gurinovich.bamper.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);


    @Query(value = """
    select 1 from posts where user_id = :user_id and id = :post_id
    """)
    boolean isPostOwner(@Param("post_id") Integer post_id,@Param("user_id") Integer user_id);
}
