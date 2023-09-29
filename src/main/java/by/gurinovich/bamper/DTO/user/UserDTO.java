package by.gurinovich.bamper.DTO.user;

import by.gurinovich.bamper.utils.validation.OnCreate;
import by.gurinovich.bamper.utils.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class UserDTO {

    @NotNull(message = "Id must not be null", groups = {OnUpdate.class})
    private Integer id;

    @NotNull(message = "Username must not be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(message = "Username length must be between 1 and 25 characters",
            groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must not be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(message = "Password length should be between 5 and 25 characters",
            groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must not be null", groups = {OnCreate.class, OnUpdate.class})
    @Length(min = 5, max = 25, message = "Password length should be between 5 and 25 characters",
            groups = {OnCreate.class, OnUpdate.class})
    private String confirmationPassword;


    @Email(message = "Email must be real", groups = {OnCreate.class, OnUpdate.class})
    private String email;


    @Length (min = 5, message = "Number must be valid", groups = {OnCreate.class, OnUpdate.class})
    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String dateOfRegistration;

    public UserDTO(Integer id, String username, String password, String email, String phoneNumber, String dateOfRegistration) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfRegistration = dateOfRegistration;
    }
}
