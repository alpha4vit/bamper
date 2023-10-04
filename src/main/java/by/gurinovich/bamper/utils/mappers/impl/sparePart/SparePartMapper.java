package by.gurinovich.bamper.utils.mappers.impl.sparePart;

import by.gurinovich.bamper.DTO.spareParts.SparePartDTO;
import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import by.gurinovich.bamper.utils.mappers.Mappable;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SparePartMapper implements Mappable<SparePart, SparePartDTO> {

    private final ModelMapper modelMapper;

    @Override
    public SparePart fromDTO(SparePartDTO dto) {
        return modelMapper.map(dto, SparePart.class);
    }

    @Override
    public SparePartDTO toDTO(SparePart entity) {
        return modelMapper.map(entity, SparePartDTO.class);
    }

    @Override
    public List<SparePartDTO> toDTOs(List<SparePart> entities) {
        return entities.stream().map(this::toDTO).toList();
    }

    @Override
    public List<SparePart> toEntities(List<SparePartDTO> dtos) {
        return dtos.stream().map(this::fromDTO).toList();
    }
}
