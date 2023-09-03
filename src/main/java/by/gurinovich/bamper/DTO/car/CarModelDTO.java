package by.gurinovich.bamper.DTO.car;

import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.models.carsEntities.CarModelGeneration;
import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import by.gurinovich.bamper.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CarModelDTO {
    private int id;
    private String name;
    private List<CarModelGenerationDTO> carModelGenerationDTOs;
    private List<SparePartDTO> sparePartDTOs;


    public CarModelDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CarModelDTO(int id, String name, List<CarModelGenerationDTO> carModelGenerationDTOs, List<SparePartDTO> sparePartDTOs) {
        this.id = id;
        this.name = name;
        this.carModelGenerationDTOs = carModelGenerationDTOs;
        this.sparePartDTOs = sparePartDTOs;
    }
}
