package by.gurinovich.bamper.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Spare part creation DTO")
public class SparePartCreatingRequest {
    @Schema(name = "Spare part name id", example = "1")
    private Integer name_id;
    @Schema(name = "Spare part name", example = "Колесо")
    private String name;
    @Schema(name = "Spare part serial number", example = "213153431")
    private String number;
}
