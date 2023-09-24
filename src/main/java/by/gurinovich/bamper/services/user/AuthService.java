package by.gurinovich.bamper.services.user;

import by.gurinovich.bamper.models.user.Role;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.requests.auth.JWTRequest;
import by.gurinovich.bamper.responses.auth.JWTResponse;
import by.gurinovich.bamper.security.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;


    public JWTResponse login(JWTRequest jwtRequest){
        JWTResponse jwtResponse = new JWTResponse();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),
                        jwtRequest.getPassword()));
        User user = userService.getByUsername(jwtRequest.getUsername());
        jwtResponse.setId(user.getId());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(), user.getUsername(), user.getRoles()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(), user.getUsername()));
        return jwtResponse;
    }


    public JWTResponse refresh(String token){
        return jwtTokenProvider.refreshUserTokens(token);
    }

}
