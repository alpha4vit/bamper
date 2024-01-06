package by.gurinovich.bamper.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import kotlin.BuilderInference;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Post creation")
public class PostCreation {
    @Schema(name = "title", example = "Продам колесо R19")
    private String title;

    @Schema(name = "spare_part_id", example = "1")
    @JsonProperty(value = "spare_part_id")
    private Long sparePartId;

    @Schema(name = "address", example = "проспект Пушкина 30, Минск")
    private String address;
}
