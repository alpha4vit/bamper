package by.gurinovich.bamper.models.user;

import by.gurinovich.bamper.models.postsEntities.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "username")
    private String username;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Column(name = "email")
    private String email;

    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles")
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_registration")
    @JsonFormat(pattern = "yyyy-MM")
    private Calendar dateOfRegistration;

    @OneToMany(mappedBy = "author")
    private List<Review> reviewsBy;

    @OneToMany(mappedBy = "user")
    private List<Review> reviewsFor;

    @NotNull
    private boolean enabled;

    public User(Long id, String username, String password, String email, Set<Role> roles, String phoneNumber, Calendar dateOfRegistration, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.phoneNumber = phoneNumber;
        this.dateOfRegistration = dateOfRegistration;
        this.enabled = enabled;
    }

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

}
