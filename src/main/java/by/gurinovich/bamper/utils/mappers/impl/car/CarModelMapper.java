package by.gurinovich.bamper.utils.mappers.impl.car;

import by.gurinovich.bamper.DTO.car.CarModelDTO;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.models.carsEntities.CarModelGeneration;
import by.gurinovich.bamper.services.car.CarModelGenerationService;
import by.gurinovich.bamper.utils.mappers.Mappable;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CarModelMapper implements Mappable<CarModel, CarModelDTO> {
    private final ModelMapper modelMapper;
    private final CarModelGenerationMapper carModelGenerationMapper;
    @Override
    public CarModel fromDTO(CarModelDTO dto) {
        return modelMapper.map(dto, CarModel.class);
    }

    @Override
    public CarModelDTO toDTO(CarModel entity) {
        CarModelDTO carModelDTO = modelMapper.map(entity, CarModelDTO.class);
        carModelDTO.setCarModelGenerationDTOs(carModelGenerationMapper.toDTOs(entity.getCarModelGenerations()));
        return carModelDTO;
    }

    @Override
    public List<CarModelDTO> toDTOs(List<CarModel> entities) {
        return entities.stream().map(this::toDTO).toList();
    }

    @Override
    public List<CarModel> toEntities(List<CarModelDTO> dtos) {
        return dtos.stream().map(this::fromDTO).toList();
    }
}
