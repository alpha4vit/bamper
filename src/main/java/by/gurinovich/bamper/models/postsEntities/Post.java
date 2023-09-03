package by.gurinovich.bamper.models.postsEntities;

import by.gurinovich.bamper.security.User;
import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotBlank
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "spare_part_id", referencedColumnName = "id")
    private SparePart sparePart;

}
