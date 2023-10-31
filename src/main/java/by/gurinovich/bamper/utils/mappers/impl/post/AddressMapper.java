package by.gurinovich.bamper.utils.mappers.impl.post;

import by.gurinovich.bamper.DTO.user.AddressDTO;
import by.gurinovich.bamper.models.user.Address;
import by.gurinovich.bamper.utils.mappers.Mappable;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.crypto.Mac;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AddressMapper implements Mappable<Address, AddressDTO> {
    private final ModelMapper modelMapper;

    @Override
    public Address fromDTO(AddressDTO dto) {
        return modelMapper.map(dto, Address.class);
    }

    @Override
    public AddressDTO toDTO(Address entity) {
        return modelMapper.map(entity, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> toDTOs(List<Address> entities) {
        return entities.stream().map(this::toDTO).toList();
    }

    @Override
    public List<Address> toEntities(List<AddressDTO> dtos) throws Exception {
        return dtos.stream().map(this::fromDTO).toList();
    }
}
