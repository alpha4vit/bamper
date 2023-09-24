package by.gurinovich.bamper.DTO.post;

import by.gurinovich.bamper.DTO.user.UserDTO;
import by.gurinovich.bamper.DTO.spareParts.SparePartDTO;
import lombok.Data;

@Data
public class PostDTO {
    private Integer id;
    private String title;
    private UserDTO userDTO;
    private SparePartDTO sparePartDTO;
}
