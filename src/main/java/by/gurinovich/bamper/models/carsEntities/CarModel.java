package by.gurinovich.bamper.models.carsEntities;


import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "car_models")
@NoArgsConstructor
public class CarModel  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "model_name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_brand_id", referencedColumnName = "id")
    private CarBrand carBrand;

    @OneToMany(mappedBy = "carModel", fetch = FetchType.EAGER)
    private List<CarModelGeneration> carModelGenerations;

    @ManyToMany(mappedBy = "carModels")
    private List<SparePart> spareParts;


    public CarModel(String name, CarBrand carBrand) {
        this.name = name;
        this.carBrand = carBrand;
    }
}
