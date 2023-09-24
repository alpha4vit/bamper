package by.gurinovich.bamper.security;

import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JWTUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException{
        User user = userService.getByUsername(username);
        return JWTEntityFactory.create(user);
    }
}
