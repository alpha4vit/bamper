package by.gurinovich.bamper.DTO.user;

import by.gurinovich.bamper.utils.validation.OnCreate;
import by.gurinovich.bamper.utils.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "User DTO")
public class UserDTO {

    @Schema(name = "User id", example = "1")
    @NotNull(message = "Id must not be null", groups = {OnUpdate.class})
    private Integer id;

    @Schema(name = "Username", example = "alpha4vit")
    @NotNull(message = "Username must not be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(message = "Username length must be between 1 and 25 characters",
            groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @Schema(name = "User password", example = "alpha4vit")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must not be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(message = "Password length should be between 5 and 25 characters",
            groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @Schema(name = "User password confirmation", example = "alpha4vit")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must not be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 5, max = 25, message = "Password length should be between 5 and 25 characters",
            groups = {OnCreate.class, OnUpdate.class})
    private String confirmationPassword;

    @Schema(name = "User email", example = "alpha@gmail.com")
    @Email(message = "Email must be real", groups = {OnCreate.class, OnUpdate.class})
    private String email;

    @Schema(name = "User phone number", example = "+375441234567")
    @Length (min = 5, message = "Number must be valid", groups = {OnCreate.class, OnUpdate.class})
    private String phoneNumber;

    @Schema(name = "User date of registration", example = "2002-01")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM")
    private String dateOfRegistration;

    @Schema(name = "Reviews created by this user")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<ReviewDTO> reviewsBy;

    @Schema(name = "Reviews for this user")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<ReviewDTO> reviewsFor;

}
