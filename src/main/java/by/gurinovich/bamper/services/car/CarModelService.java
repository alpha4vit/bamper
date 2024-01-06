package by.gurinovich.bamper.services.car;

import by.gurinovich.bamper.DTO.car.CarModelDTO;
import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.repositories.car.CarModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarModelService {
    private final CarModelRepository carModelRepository;

    public List<CarModel> getAllModelForBrand(CarBrand carBrand){
        return carModelRepository.findByCarBrand(carBrand);
    }

    public CarModel getById(Integer id){
        Optional<CarModel> carModel = carModelRepository.findById(id);
        if (carModel.isEmpty())
            throw new ResourceNotFoundException("CarModel with this id not found");
        return carModel.get();
    }

    public void deleteById(Integer id){
        if (carModelRepository.findById(id).isPresent())
            carModelRepository.deleteById(id);
        else
            throw new ResourceNotFoundException("CarModel with this id not found");
    }

    @Transactional
    public boolean save(CarBrand carBrand, CarModelDTO carModelDTO){
        List<CarModel> models = carModelRepository.findByCarBrand(carBrand);
        if (models.stream().anyMatch(el -> el.getName().equals(carModelDTO.getName())))
            throw new InvalidOperationException("CarModel for this brand already exists");
        carModelRepository.save(new CarModel(carModelDTO.getName(), carBrand));
        return true;
    }

}
