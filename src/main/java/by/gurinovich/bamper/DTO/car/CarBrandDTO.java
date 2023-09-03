package by.gurinovich.bamper.DTO.car;

import by.gurinovich.bamper.utils.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CarBrandDTO {
    @JsonView(Views.Internal.class)
    private int id;
    @JsonView(Views.Internal.class)
    private String name;
    @JsonView(Views.Extended.class)
    private List<CarModelDTO> models;
}
    