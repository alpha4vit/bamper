package by.gurinovich.bamper.services.car;

import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.repositories.car.CarBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CarBrandService {
    private final CarBrandRepository carBrandRepository;


    public CarBrand getById(Integer id){
        Optional<CarBrand> carBrand = carBrandRepository.findById(id);
        if (carBrand.isEmpty())
            throw new ResourceNotFoundException("CarBrand with this id not found");
        return carBrand.get();
    }

    public List<CarBrand> getAll(){
        return carBrandRepository.findAll();
    }

    @Transactional
    public boolean save(CarBrand carBrand){
        Optional<CarBrand> check = carBrandRepository.findByName(carBrand.getName());
        if (check.isPresent())
            throw new InvalidOperationException("CarBrand with this id already exists");
        carBrandRepository.save(carBrand);
        System.exit(0);
        return true;
    }

    @Transactional
    public void deleteById(Integer id){
        if (carBrandRepository.findById(id).isEmpty())
            throw new ResourceNotFoundException("CarBrand with this id not found");
        carBrandRepository.deleteById(id);

    }

}
