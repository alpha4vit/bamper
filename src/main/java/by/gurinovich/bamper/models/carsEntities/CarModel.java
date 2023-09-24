package by.gurinovich.bamper.models.carsEntities;


import by.gurinovich.bamper.DTO.car.CarModelDTO;
import by.gurinovich.bamper.DTO.car.CarModelGenerationDTO;
import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import by.gurinovich.bamper.services.car.CarModelGenerationService;
import by.gurinovich.bamper.services.sparePart.SparePartService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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

    public static CarModelDTO convertToDTO(CarModel carModel){
        return new CarModelDTO(carModel.getId(), carModel.getName(),
                carModel.getCarModelGenerations().stream().map(CarModelGenerationService::convertToDTO).toList(),
                carModel.getSpareParts().stream().map(SparePartService::converToDTO).toList());
    }

    public CarModel(String name, CarBrand carBrand) {
        this.name = name;
        this.carBrand = carBrand;
    }
}
