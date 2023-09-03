package by.gurinovich.bamper.DTO.car;

import by.gurinovich.bamper.models.sparePartsEntities.SparePartName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SparePartDTO {
    private int id;
    private SparePartNameDTO name;
    private String number;
}
