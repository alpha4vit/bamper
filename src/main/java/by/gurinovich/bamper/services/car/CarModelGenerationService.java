package by.gurinovich.bamper.services.car;

import by.gurinovich.bamper.DTO.car.CarModelGenerationDTO;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.models.carsEntities.CarModelGeneration;
import by.gurinovich.bamper.repositories.car.CarModelGenerationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CarModelGenerationService {
    private final CarModelGenerationRepo carModelGenerationRepo;

    @Autowired
    public CarModelGenerationService(CarModelGenerationRepo carModelGenerationRepo) {
        this.carModelGenerationRepo = carModelGenerationRepo;
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

    @Transactional
    public boolean deleteById(Integer id){
        if(carModelGenerationRepo.findById(id).isPresent()){
            carModelGenerationRepo.deleteById(id);
            return true;
        }
        return false;
    }


    public static CarModelGenerationDTO convertToDTO(CarModelGeneration carModelGeneration){
        return new CarModelGenerationDTO(carModelGeneration.getId(),
                carModelGeneration.getName(),
                carModelGeneration.getStartYearOfProduction().toString(),
                carModelGeneration.getEndYearOfProduction().toString());
    }
}
