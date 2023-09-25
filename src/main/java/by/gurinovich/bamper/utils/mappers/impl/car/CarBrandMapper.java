package by.gurinovich.bamper.utils.mappers.impl.car;

import by.gurinovich.bamper.DTO.car.CarBrandDTO;
import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.services.car.CarModelService;
import by.gurinovich.bamper.utils.mappers.Mappable;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.crypto.Mac;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CarBrandMapper implements Mappable<CarBrand, CarBrandDTO> {
    private final ModelMapper modelMapper;
    private final CarModelMapper carModelMapper;

    @Override
    public CarBrand fromDTO(CarBrandDTO dto) {
        return modelMapper.map(dto, CarBrand.class);
    }

    @Override
    public CarBrandDTO toDTO(CarBrand entity) {
        CarBrandDTO carBrandDTO = modelMapper.map(entity, CarBrandDTO.class);
        carBrandDTO.setModels(carModelMapper.toDTOs(entity.getModels()));
        return carBrandDTO;
    }

    @Override
    public List<CarBrandDTO> toDTOs(List<CarBrand> entities) {
        return entities.stream().map(this::toDTO).toList();
    }

    @Override
    public List<CarBrand> toEntities(List<CarBrandDTO> dtos) {
        return dtos.stream().map(this::fromDTO).toList();
    }
}
