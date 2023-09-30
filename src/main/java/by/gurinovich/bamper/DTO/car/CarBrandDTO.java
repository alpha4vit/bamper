package by.gurinovich.bamper.DTO.car;

import by.gurinovich.bamper.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Car Brand DTO")
public class CarBrandDTO {
    @Schema(name = "Car brand id", example = "1")
    private int id;
    @Schema(name = "Car brand name", example = "BMW")
    private String name;
    @Schema(name = "Car brand models")
    private List<CarModelDTO> models;
}
    