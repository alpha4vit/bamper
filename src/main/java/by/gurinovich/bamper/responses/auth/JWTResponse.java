package by.gurinovich.bamper.responses.auth;


import lombok.Data;

@Data
public final class JWTResponse {
    private Integer id;
    private String username;
    private String accessToken;
    private String refreshToken;
}
