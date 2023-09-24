package by.gurinovich.bamper.security;

import by.gurinovich.bamper.models.user.Role;
import by.gurinovich.bamper.models.user.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class JWTEntityFactory {
    public static JWTEntity create(final User user){
        return new JWTEntity(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRoles())
        );
    }

    private static List<SimpleGrantedAuthority> mapToGrantedAuthorities(final Set<Role> roles) {
        return roles.stream().map(Enum::name).map(SimpleGrantedAuthority::new).toList();
    }


}
