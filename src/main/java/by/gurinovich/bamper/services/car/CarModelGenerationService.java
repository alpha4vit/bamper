package by.gurinovich.bamper.services.car;

import by.gurinovich.bamper.DTO.car.CarModelGenerationDTO;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.models.carsEntities.CarModelGeneration;
import by.gurinovich.bamper.repositories.car.CarModelGenerationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarModelGenerationService {
    private final CarModelGenerationRepo carModelGenerationRepo;

    public CarModelGeneration getById(Integer id){
        return carModelGenerationRepo.findById(id).orElse(null);
    }

    @Transactional
    public boolean deleteById(Integer id){
        if(carModelGenerationRepo.findById(id).isPresent()){
            carModelGenerationRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public CarModelGeneration save(CarModel carModel, CarModelGenerationDTO carModelGenerationDTO) throws ParseException {
        CarModelGeneration carModelGeneration = new CarModelGeneration(carModel,
                carModelGenerationDTO.getName(),
                new SimpleDateFormat("yyyy-MM").parse(carModelGenerationDTO.getStartYearOfProduction()),
                new SimpleDateFormat("yyyy-MM").parse(carModelGenerationDTO.getEndYearOfProduction()));
        return carModelGenerationRepo.save(carModelGeneration);
    }

    public List<CarModelGeneration> getAllGenerationsForModel(CarModel carModel){
        return carModelGenerationRepo.findAllByCarModel(carModel);
    }

    public static CarModelGenerationDTO convertToDTO(CarModelGeneration carModelGeneration){
        return new CarModelGenerationDTO(carModelGeneration.getId(),
                carModelGeneration.getName(),
                carModelGeneration.getStartYearOfProduction().toString(),
                carModelGeneration.getEndYearOfProduction().toString());
    }
}
