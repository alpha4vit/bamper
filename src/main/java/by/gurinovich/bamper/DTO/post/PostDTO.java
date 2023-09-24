package by.gurinovich.bamper.DTO.post;

import by.gurinovich.bamper.DTO.user.UserDTO;
import by.gurinovich.bamper.DTO.spareParts.SparePartDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    private String title;

    private UserDTO userDTO;

    private SparePartDTO sparePartDTO;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> images;
}
