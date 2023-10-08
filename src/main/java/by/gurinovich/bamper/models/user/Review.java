package by.gurinovich.bamper.models.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @Size(min = 5, max = 50, message = "Size of title must be between 5 and 50 characters")
    private String title;

    @Column(name = "text")
    @Size(min = 1, max = 1000, message = "Size of title must be between 1 and 1000 characters")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "stars")
    @Min(value = 1, message = "Minimum value of stars is 1")
    @Max(value = 5, message = "Maximum value of stars is 5")
    private Integer stars;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

}
