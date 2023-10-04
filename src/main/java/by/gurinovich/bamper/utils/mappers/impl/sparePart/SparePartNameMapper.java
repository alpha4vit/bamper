package by.gurinovich.bamper.utils.mappers.impl.sparePart;

import by.gurinovich.bamper.DTO.spareParts.SparePartNameDTO;
import by.gurinovich.bamper.models.sparePartsEntities.SparePartName;
import by.gurinovich.bamper.utils.mappers.Mappable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SparePartNameMapper implements Mappable<SparePartName, SparePartNameDTO> {
    private final ModelMapper modelMapper;

    @Override
    public SparePartName fromDTO(SparePartNameDTO dto) {
        return modelMapper.map(dto, SparePartName.class);
    }

    @Override
    public SparePartNameDTO toDTO(SparePartName entity) {
        return modelMapper.map(entity, SparePartNameDTO.class);
    }

    @Override
    public List<SparePartNameDTO> toDTOs(List<SparePartName> entities) {
        return entities.stream().map(this::toDTO).toList();
    }

    @SneakyThrows
    @Override
    public List<SparePartName> toEntities(List<SparePartNameDTO> dtos) {
        return dtos.stream().map(this::fromDTO).toList();
    }
}
