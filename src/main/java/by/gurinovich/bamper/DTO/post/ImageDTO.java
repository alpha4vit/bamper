package by.gurinovich.bamper.DTO.post;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageDTO {

    @NotNull(message = "Image must not be null")
    private MultipartFile multipartFile;
}
