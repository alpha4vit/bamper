package by.gurinovich.bamper.models;

import by.gurinovich.bamper.models.postsEntities.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name = "user_name")
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String typeOfSeller;

    private String phoneNumber;

    private Calendar dateOfRegistration;

    public User(String username, String password, String email, Role role, String typeOfSeller, String phoneNumber, Calendar dateOfRegistration) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.typeOfSeller = typeOfSeller;
        this.phoneNumber = phoneNumber;
        this.dateOfRegistration = dateOfRegistration;
    }

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
