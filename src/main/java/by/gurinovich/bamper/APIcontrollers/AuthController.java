package by.gurinovich.bamper.APIcontrollers;

import by.gurinovich.bamper.DTO.user.UserDTO;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.requests.auth.JWTRequest;
import by.gurinovich.bamper.responses.auth.JWTResponse;
import by.gurinovich.bamper.services.user.AddressService;
import by.gurinovich.bamper.services.user.AuthService;
import by.gurinovich.bamper.services.user.UserService;
import by.gurinovich.bamper.utils.mappers.impl.user.UserMapper;
import by.gurinovich.bamper.utils.validation.OnCreate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Arrays;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final AddressService addressService;

    @PostMapping("/login")
    public JWTResponse login(@Validated @RequestBody JWTRequest loginRequest){
        String coordinates = addressService.getCoordinatesByAddress("проспект Независимости, 62, Минск");
        System.out.println(coordinates);
        System.out.println(addressService.getAddressByCoordinates(coordinates));
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDTO register(@Validated(OnCreate.class) @RequestBody UserDTO userDTO) throws ParseException {
        User user = userMapper.fromDTO(userDTO);
        User createdUser = userService.save(user);
        return userMapper.toDTO(createdUser);
    }

    @Schema(name = "Refresh token", example = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJyb21hMyIsImlkIjoxLCJyb2xlcyI6WyJVU0VSIiwiQURNSU4iXSwiaWF0IjoxNjk2MTA4ODUxLCJleHAiOjE2OTg3MDA4NTF9.PM8hcus9h8rEhRvx30y8UxDMcYzR9tQG6xc_7rbiGt3EMjnJfo9rucDjYYxKCroceyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJyb21hMyIsImlkIjoxLCJyb2xlcyI6WyJVU0VSIiwiQURNSU4iXSwiaWF0IjoxNjk2MTA4ODUxLCJleHAiOjE2OTg3MDA4NTF9.PM8hcus9h8rEhRvx30y8UxDMcYzR9tQG6xc_7rbiGt3EMjnJfo9rucDjYYxKCroc")
    @PostMapping("/refresh")
    public JWTResponse refresh(@RequestBody String refreshToken){
        return authService.refresh(refreshToken);
    }

    @GetMapping("/confirm/{user_id}")
    public void confirm(@PathVariable("user_id") Long userId){
        userService.enableUser(userId);
    }
}
