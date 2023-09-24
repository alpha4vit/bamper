package by.gurinovich.bamper.APIcontrollers;

import by.gurinovich.bamper.DTO.user.UserDTO;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.requests.auth.JWTRequest;
import by.gurinovich.bamper.responses.auth.JWTResponse;
import by.gurinovich.bamper.services.user.AuthService;
import by.gurinovich.bamper.services.user.UserService;
import by.gurinovich.bamper.utils.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public JWTResponse login(@Validated @RequestBody JWTRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDTO register(@Validated(OnCreate.class) @RequestBody UserDTO userDTO) throws ParseException {
        User user = UserService.convertFromDTO(userDTO);
        User createdUser = userService.save(user);
        return UserService.convertToDTO(createdUser);
    }

    @PostMapping("/refresh")
    public JWTResponse refresh(@RequestBody String refreshToken){
        return authService.refresh(refreshToken);
    }
}
