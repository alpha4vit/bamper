package by.gurinovich.bamper.security.services;

import by.gurinovich.bamper.requests.auth.AuthRequest;
import by.gurinovich.bamper.responses.auth.AuthResponse;
import by.gurinovich.bamper.security.UserRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;

    public AuthResponse register(AuthRequest request){
        var user = User.builder().
        return null
    }

    public AuthRequest login(AuthRequest request){
        return null;
    }
}
