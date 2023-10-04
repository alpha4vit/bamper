package by.gurinovich.bamper.DTO.car;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Car model generation DTO")
public class CarModelGenerationDTO {
    @Schema(name = "Car model generation id", example = "1")
    private int id;
    @Schema(name = "Car model generation name", example = "E39")
    private String name;
    @Schema(name = "Car model generation start year of production", example = "2002-01")
    private String startYearOfProduction;
    @Schema(name = "Car model generation end year of production", example = "2002-01")
    private String endYearOfProduction;
}
