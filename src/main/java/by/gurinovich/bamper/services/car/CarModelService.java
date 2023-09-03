package by.gurinovich.bamper.services.car;

import by.gurinovich.bamper.DTO.car.CarModelDTO;
import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.repositories.car.CarBrandRepo;
import by.gurinovich.bamper.repositories.car.CarModelRepo;
import by.gurinovich.bamper.services.sparePart.SparePartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarModelService {
    private final CarModelRepo carModelRepo;


    public static CarModelDTO convertToDTO(CarModel carModel){
        return new CarModelDTO(carModel.getId(),
                carModel.getName(),
                carModel.getCarModelGenerations().stream().map(CarModelGenerationService::convertToDTO).toList(),
                carModel.getSpareParts().stream().map(SparePartService::converToDTO).toList());
    }

    public List<CarModel> getAllModelForBrand(CarBrand carBrand){
        return carModelRepo.findByCarBrand(carBrand);
    }

    public CarModel findById(Integer id){
        return carModelRepo.findById(id).orElse(null);
    }

    public boolean deleteById(Integer id){
        if (carModelRepo.findById(id).isPresent()) {
            carModelRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public void save(CarBrand carBrand, CarModelDTO carModelDTO){
        carModelRepo.save(new CarModel(carModelDTO.getName(), carBrand));
    }

}
