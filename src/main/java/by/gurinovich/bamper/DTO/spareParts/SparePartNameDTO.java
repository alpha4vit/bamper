package by.gurinovich.bamper.DTO.spareParts;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Spare part name DTO")
public class SparePartNameDTO {
    @Schema(name = "Spare part name id", example = "1")
    private int id;
    @Schema(name = "Spare part name", example = "Колесо")
    private String name;
}
