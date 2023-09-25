package by.gurinovich.bamper.utils.mappers.impl.car;

import by.gurinovich.bamper.DTO.car.CarModelGenerationDTO;
import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.models.carsEntities.CarModelGeneration;
import by.gurinovich.bamper.utils.mappers.Mappable;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CarModelGenerationMapper implements Mappable<CarModelGeneration, CarModelGenerationDTO> {



    @Override
    public CarModelGeneration fromDTO(CarModelGenerationDTO dto) {
        try {
            return new CarModelGeneration(dto.getId(),
                    dto.getName(),
                    new SimpleDateFormat("yyyy-MM").parse(dto.getStartYearOfProduction()),
                    new SimpleDateFormat("yyyy-MM").parse(dto.getEndYearOfProduction()));
        }
        catch (ParseException ex){
            throw new InvalidOperationException("Data must match to this pattern: 'yyyy-MM");
        }
    }


    @Override
    public CarModelGenerationDTO toDTO(CarModelGeneration carModelGeneration) {
        return new CarModelGenerationDTO(carModelGeneration.getId(),
                carModelGeneration.getName(),
                carModelGeneration.getStartYearOfProduction().toString(),
                carModelGeneration.getEndYearOfProduction().toString());
    }

    @Override
    public List<CarModelGenerationDTO> toDTOs(List<CarModelGeneration> entities) {
        return entities.stream().map(this::toDTO).toList();
    }

    @Override
    public List<CarModelGeneration> toEntities(List<CarModelGenerationDTO> dtos) {
        return dtos.stream().map(this::fromDTO).toList();
    }
}
