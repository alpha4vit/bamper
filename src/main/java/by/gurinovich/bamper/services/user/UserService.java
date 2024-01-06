package by.gurinovich.bamper.services.user;

import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.user.Role;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.repositories.user.UserRepository;
import by.gurinovich.bamper.security.JWTEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getAuthorizedUser(){
        JWTEntity jwtEntity = (JWTEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(jwtEntity.getId()).orElseThrow(() -> new ResourceNotFoundException("Not authorized!"));
    }

    public User getByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new ResourceNotFoundException("User with this username not found"));
    }

    public User getById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException("User with this id not found"));
    }

    public boolean isPostOwner(Long id, Long post_id){
        return getById(id).getPosts().stream().anyMatch(el -> el.getId() == post_id);
    }

    @Transactional(readOnly = true)
    public User getByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new ResourceNotFoundException("User with this email not found"));
    }


    @Transactional
    public User save(User user){
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new IllegalStateException("User with this username already exists");
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new IllegalStateException("User with this email already exists");
        user.setDateOfRegistration(new GregorianCalendar());
        user.setRoles(new HashSet<>(List.of(Role.USER)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User created = userRepository.save(user);
        emailService.sendEmailMessage(created, EmailType.REGISTRATION, new Properties());
        return created;
    }

    @Transactional
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    @Transactional
    public void enableUser(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty())
            throw new ResourceNotFoundException("User with this id not found");
        User user = userOptional.get();
        user.setEnabled(true);
        userRepository.save(user);
    }

}
