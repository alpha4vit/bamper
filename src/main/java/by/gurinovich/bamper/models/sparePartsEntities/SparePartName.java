package by.gurinovich.bamper.models.sparePartsEntities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "spare_part_names")
public class SparePartName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "spare_part_name")
    private String Name;

    @OneToMany(mappedBy = "name")
    private List<SparePart> spareParts;
}
