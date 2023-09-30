package by.gurinovich.bamper.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Post creation")
public class PostCreation {
    @Schema(name = "Post title", example = "Продам колесо R19")
    private String title;
    @Schema(name = "Spare part id", example = "1")
    private Integer sparePartId;
}
