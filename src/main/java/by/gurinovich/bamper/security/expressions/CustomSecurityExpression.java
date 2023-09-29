package by.gurinovich.bamper.security.expressions;

import by.gurinovich.bamper.security.JWTEntity;
import by.gurinovich.bamper.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserService userService;

    public boolean isPostOwner(final Integer post_id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JWTEntity jwtEntity = (JWTEntity) authentication.getPrincipal();
        System.out.println(jwtEntity.getUsername());
        return userService.isPostOwner(jwtEntity.getId(), post_id);
    }
}
