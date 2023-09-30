package by.gurinovich.bamper.DTO.car;

import by.gurinovich.bamper.DTO.spareParts.SparePartDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "Car model DTO")
public class CarModelDTO {
    @Schema(name = "Car model id", example = "1")
    private int id;
    @Schema(name = "Car model name", example = "Astra")
    private String name;
    @Schema(name = "Car model generations")
    private List<CarModelGenerationDTO> carModelGenerationDTOs;
    @Schema(name = "Spare parts for this car model", example = "1")
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
