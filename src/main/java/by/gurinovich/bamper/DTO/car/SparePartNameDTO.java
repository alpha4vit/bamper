package by.gurinovich.bamper.DTO.car;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SparePartNameDTO {
    private int id;
    private String name;
}
