package by.gurinovich.bamper.models.sparePartsEntities;

import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.models.postsEntities.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "spare_parts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SparePart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "spare_part_name_id", referencedColumnName = "id")
    private SparePartName name;

    private String number;

    @ManyToMany
    @JoinTable(name = "car_model_spare_part",
            joinColumns = @JoinColumn(name = "spare_part_id"),
            inverseJoinColumns = @JoinColumn(name= "car_model_id"))
    private List<CarModel> carModels;

    @OneToMany(mappedBy = "sparePart")
    private List<Post> posts;

    public SparePart(SparePartName name, String number) {
        this.name = name;
        this.number = number;
    }
}
