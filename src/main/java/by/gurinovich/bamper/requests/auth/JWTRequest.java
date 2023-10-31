package by.gurinovich.bamper.requests.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Login request")
public class JWTRequest {
    @Schema(name = "username", example = "roma3")
    @NotNull
    public String username;

    @Schema(name = "password", example = "roma")
    @NotNull
    public String password;
}

