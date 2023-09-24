package by.gurinovich.bamper.services.car;

import by.gurinovich.bamper.DTO.car.CarBrandDTO;
import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.repositories.car.CarBrandRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarBrandService {
    private final CarBrandRepo carBrandRepo;


    public CarBrand getById(Integer id){
        return carBrandRepo.findById(id).orElse(null);
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

    public static CarBrandDTO convertToDTO(CarBrand carBrand){
        return new CarBrandDTO(carBrand.getId(), carBrand.getName(), carBrand.getModels().stream().map(CarModel::convertToDTO).toList());
    }
}
