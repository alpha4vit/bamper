package by.gurinovich.bamper.DTO.car;

import by.gurinovich.bamper.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarBrandDTO {
    private int id;
    private String name;
    private List<CarModelDTO> models;
}
    