package by.gurinovich.bamper.DTO.spareParts;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Spare part DTO")
public class SparePartDTO {
    @Schema(name = "Spare part id", example = "1")
    private int id;
    @Schema(name = "Spare part name")
    private SparePartNameDTO name;
    @Schema(name = "Spare part serial number", example = "1312312312")
    private String number;
}
