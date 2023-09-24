package by.gurinovich.bamper.models.postsEntities;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Image {
    private MultipartFile multipartFile;
}
