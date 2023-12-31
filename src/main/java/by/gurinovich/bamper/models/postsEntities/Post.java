package by.gurinovich.bamper.models.postsEntities;

import by.gurinovich.bamper.models.user.Address;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotBlank
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spare_part_id", referencedColumnName = "id")
    private SparePart sparePart;

    @Column(name = "image")
    @CollectionTable(name = "posts_images")
    @ElementCollection
    private List<String> images;

    @OneToMany(mappedBy = "post")
    private List<Address> addresses;

    public Post(String title, User user, SparePart sparePart) {
        this.title = title;
        this.user = user;
        this.sparePart = sparePart;
    }
}
