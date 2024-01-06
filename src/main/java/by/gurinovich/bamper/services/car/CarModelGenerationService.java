package by.gurinovich.bamper.services.car;

import by.gurinovich.bamper.DTO.car.CarModelGenerationDTO;
import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.models.carsEntities.CarModelGeneration;
import by.gurinovich.bamper.repositories.car.CarModelGenerationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarModelGenerationService {
    private final CarModelGenerationRepository carModelGenerationRepository;

    public CarModelGeneration getById(Integer id){
        Optional<CarModelGeneration> carModelGeneration = carModelGenerationRepository.findById(id);
        if (carModelGeneration.isEmpty())
            throw new ResourceNotFoundException("CarModelGeneration with this id not found");
        return carModelGeneration.get();
    }

    @Transactional
    public void deleteById(Integer id){
        if(carModelGenerationRepository.findById(id).isPresent())
            carModelGenerationRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("CarModelGeneration with this id not found");
    }

    @Transactional
    public CarModelGeneration save(CarModel carModel, CarModelGenerationDTO carModelGenerationDTO) throws ParseException {
        List<CarModelGeneration> generationsForCarModel = carModelGenerationRepository.findAllByCarModel(carModel);
        if (generationsForCarModel.stream().anyMatch(el -> el.getName().equals(carModelGenerationDTO.getName())))
            throw new InvalidOperationException("CarModelGeneration for this model with this name already exists");
        CarModelGeneration carModelGeneration = new CarModelGeneration(carModel,
                carModelGenerationDTO.getName(),
                new SimpleDateFormat("yyyy-MM").parse(carModelGenerationDTO.getStartYearOfProduction()),
                new SimpleDateFormat("yyyy-MM").parse(carModelGenerationDTO.getEndYearOfProduction()));
        return carModelGenerationRepository.save(carModelGeneration);
    }

    public List<CarModelGeneration> getAllGenerationsForModel(CarModel carModel){
        return carModelGenerationRepository.findAllByCarModel(carModel);
    }

}
