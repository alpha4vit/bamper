package by.gurinovich.bamper.models.carsEntities;

import by.gurinovich.bamper.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "car_brands")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarBrand{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "brand_name")
    private String name;

    @OneToMany(mappedBy = "carBrand", fetch = FetchType.EAGER)
    private List<CarModel> models;

}
