package by.gurinovich.bamper.models.sparePartsEntities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "spare_part_names")
@NoArgsConstructor
public class SparePartName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(mappedBy = "name")
    private List<SparePart> spareParts;

    public SparePartName(String name) {
        this.name = name;
    }
}
