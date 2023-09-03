package by.gurinovich.bamper.DTO.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarModelGenerationDTO {
    private int id;
    private String name;
    private String startYearOfProduction;
    private String endYearOfProduction;
}
