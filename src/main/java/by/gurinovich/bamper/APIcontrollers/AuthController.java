package by.gurinovich.bamper.APIcontrollers;


import by.gurinovich.bamper.requests.auth.AuthRequest;
import by.gurinovich.bamper.requests.auth.RegisterRequest;
import by.gurinovich.bamper.responses.auth.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        var user = User.builder()
                .username(request.getUserName())
                .
        return null;
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request){

    }
}
