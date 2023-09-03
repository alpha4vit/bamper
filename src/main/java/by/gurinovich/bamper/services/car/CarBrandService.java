package by.gurinovich.bamper.services.car;

import by.gurinovich.bamper.DTO.car.CarBrandDTO;
import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.repositories.car.CarBrandRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarBrandService {
    private final CarBrandRepo carBrandRepo;


    public static CarBrandDTO convertToDTO(CarBrand carBrand){
        return new CarBrandDTO(carBrand.getId(), carBrand.getName(), carBrand.getModels().stream().map(CarModel::convertToDTO).toList());
    }

    public List<CarBrand> getAll(){
        return carBrandRepo.findAll();
    }

    @Transactional
    public void save(CarBrand carBrand){
        carBrandRepo.save(carBrand);
    }

    @Transactional
    public boolean deleteById(Integer id){
        if (carBrandRepo.findById(id).isPresent()){
            carBrandRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public CarBrand findById(Integer id){
        return carBrandRepo.findById(id).orElse(null);
    }

    public CarBrand findByName(String name){
        return carBrandRepo.findByName(name).orElse(null);
    }
}
