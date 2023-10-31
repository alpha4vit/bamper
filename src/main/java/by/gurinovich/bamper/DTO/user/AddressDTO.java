package by.gurinovich.bamper.DTO.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddressDTO {
    @Schema(name = "id", example = "1")
    private Long id;

    @Schema(name = "address")
    private String address;

    @Schema(name = "coordinates")
    private String coordinates;

}
