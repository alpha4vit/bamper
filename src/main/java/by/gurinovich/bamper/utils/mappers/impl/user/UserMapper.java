package by.gurinovich.bamper.utils.mappers.impl.user;

import by.gurinovich.bamper.DTO.user.UserDTO;
import by.gurinovich.bamper.models.user.User;
import by.gurinovich.bamper.utils.mappers.Mappable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bouncycastle.crypto.Mac;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mappable<User, UserDTO> {
    private final ModelMapper modelMapper;

    @Override
    public User fromDTO(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    @Override
    public UserDTO toDTO(User entity) {
        UserDTO userDTO = modelMapper.map(entity, UserDTO.class);
        userDTO.setDateOfRegistration(DateFormat.getDateInstance(DateFormat.FULL).format(entity.getDateOfRegistration().getTime()));
        return userDTO;
    }

    @Override
    public List<UserDTO> toDTOs(List<User> entities) {
        return entities.stream().map(this::toDTO).toList();
    }


    @Override
    public List<User> toEntities(List<UserDTO> dtos) {
        return dtos.stream().map(this::fromDTO).toList();
    }
}
