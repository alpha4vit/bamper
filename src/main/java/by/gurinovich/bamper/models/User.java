package by.gurinovich.bamper.models;

import by.gurinovich.bamper.models.postsEntities.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Calendar;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    private String role;

    private String typeOfSeller;

//    private Address address;

//    private List<String> phoneNumbers;

    private Calendar dateOfRegistration;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

//    private List<Review> reviews;
}
