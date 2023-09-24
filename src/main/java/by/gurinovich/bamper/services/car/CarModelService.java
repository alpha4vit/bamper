package by.gurinovich.bamper.services.car;

import by.gurinovich.bamper.DTO.car.CarModelDTO;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.repositories.car.CarModelRepo;
import by.gurinovich.bamper.services.sparePart.SparePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarModelService {
    private final CarModelRepo carModelRepo;

    public List<CarModel> getAllModelForBrand(CarBrand carBrand){
        return carModelRepo.findByCarBrand(carBrand);
    }

    public CarModel getById(Integer id){
        Optional<CarModel> carModel = carModelRepo.findById(id);
        if (carModel.isEmpty())
            throw new ResourceNotFoundException("CarModel with this id not found");
        return carModel.get();
    }

    public void deleteById(Integer id){
        if (carModelRepo.findById(id).isPresent()) {
            carModelRepo.deleteById(id);
        }
        throw new ResourceNotFoundException("CarModel with this id not found");
    }

    @Transactional
    public void save(CarBrand carBrand, CarModelDTO carModelDTO){
        carModelRepo.save(new CarModel(carModelDTO.getName(), carBrand));
    }

    public static CarModelDTO convertToDTO(CarModel carModel){
        return new CarModelDTO(carModel.getId(),
                carModel.getName(),
                carModel.getCarModelGenerations().stream().map(CarModelGenerationService::convertToDTO).toList(),
                carModel.getSpareParts().stream().map(SparePartService::converToDTO).toList());
    }


}
