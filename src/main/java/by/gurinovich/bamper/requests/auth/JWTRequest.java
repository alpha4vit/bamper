package by.gurinovich.bamper.requests.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JWTRequest {
    @NotNull
    public String username;

    @NotNull
    public String password;
}

