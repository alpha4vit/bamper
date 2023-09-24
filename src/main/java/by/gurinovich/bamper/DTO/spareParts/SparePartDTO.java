package by.gurinovich.bamper.DTO.spareParts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SparePartDTO {
    private int id;
    private SparePartNameDTO name;
    private String number;
}
