package by.gurinovich.bamper.models.carsEntities;

import by.gurinovich.bamper.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "car_brands")
public class CarBrand{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Internal.class)
    private Integer id;

    @NotBlank
    @Column(name = "brand_name")
    @JsonView(Views.Internal.class)
    private String name;

    @OneToMany(mappedBy = "carBrand", fetch = FetchType.EAGER)
    @JsonView(Views.Internal.class)
    private List<CarModel> models;

}
