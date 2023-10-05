package by.gurinovich.bamper.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Spare part creation DTO")
public class SparePartCreatingRequest {
    @Schema(name = "Spare part name id", example = "1")
    private Integer name_id;
    @Schema(name = "Spare part name", example = "Колесо")
    private String name;
    @Schema(name = "Spare part serial number", example = "213153431")
    private String number;
}
