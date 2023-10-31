package by.gurinovich.bamper.DTO.post;

import by.gurinovich.bamper.DTO.user.AddressDTO;
import by.gurinovich.bamper.DTO.user.UserDTO;
import by.gurinovich.bamper.DTO.spareParts.SparePartDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Post DTO")
public class PostDTO {
    @Schema(name = "id", example = "1")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;
    @Schema(name = "title", example = "Продам колесо R18")
    private String title;

    @Schema(name = "user")
    private UserDTO user;

    @Schema(name = "spare_part")
    private SparePartDTO sparePart;

    @Schema(name = "images", example = "{sdadsanjn231n31.png, iujkemqwkekmd321fdsa12.png}")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> images;

    @Schema(name = "addresses")
    private List<AddressDTO> addresses;
}
