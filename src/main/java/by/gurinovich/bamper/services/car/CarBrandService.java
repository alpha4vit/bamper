package by.gurinovich.bamper.services.car;

import by.gurinovich.bamper.DTO.car.CarBrandDTO;
import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.repositories.car.CarBrandRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarBrandService {
    private final CarBrandRepo carBrandRepo;


    public CarBrand getById(Integer id){
        Optional<CarBrand> carBrand = carBrandRepo.findById(id);
        if (carBrand.isEmpty())
            throw new ResourceNotFoundException("CarBrand with this id not found");
        return carBrand.get();
    }


    public List<CarBrand> getAll(){
        return carBrandRepo.findAll();
    }

    @Transactional
    public void save(CarBrand carBrand){
        Optional<CarBrand> check = carBrandRepo.findByName(carBrand.getName());
        if (check.isPresent())
            throw new InvalidOperationException("CarBrand with this id already exists");
        carBrandRepo.save(carBrand);
    }

    @Transactional
    public void deleteById(Integer id){
        if (carBrandRepo.findById(id).isPresent())
            carBrandRepo.deleteById(id);
        else
            throw new ResourceNotFoundException("CarBrand with this id not found");
    }

}
