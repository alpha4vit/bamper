package by.gurinovich.bamper.services.user;

import by.gurinovich.bamper.DTO.user.UserDTO;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.postsEntities.Post;
import by.gurinovich.bamper.models.user.Role;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.repositories.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;



    public User getByUsername(String username){
        Optional<User> user = userRepo.findByUsername(username);
        return user.orElseThrow(() -> new ResourceNotFoundException("User with this username not found"));
    }

    public User getById(Integer id){
        Optional<User> user = userRepo.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException("User with this id not found"));
    }

    public boolean isPostOwner(Integer id, Integer post_id){
        return getById(id).getPosts().stream().anyMatch(el -> el.getId() == post_id);
    }

    @Transactional(readOnly = true)
    public User getByEmail(String email){
        Optional<User> user = userRepo.findByEmail(email);
        return user.orElseThrow(() -> new ResourceNotFoundException("User with this email not found"));
    }


    @Transactional
    public User save(User user){
        if (userRepo.findByUsername(user.getUsername()).isPresent())
            throw new IllegalStateException("User with this username already exists");
        if (userRepo.findByEmail(user.getEmail()).isPresent())
            throw new IllegalStateException("User with this email already exists");
        user.setDateOfRegistration(new GregorianCalendar());
        user.setRoles(new HashSet<>(List.of(Role.USER)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Transactional
    public void deleteById(Integer id){
        userRepo.deleteById(id);
    }


    public static User convertFromDTO(UserDTO userDTO) throws ParseException {
        GregorianCalendar date = new GregorianCalendar();
        if (userDTO.getDateOfRegistration()!=null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date.setTime(sdf.parse(userDTO.getDateOfRegistration()));
        }
        return new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                new HashSet<>(List.of(Role.USER)),
                userDTO.getPhoneNumber(),
                date
        );
    }


}
