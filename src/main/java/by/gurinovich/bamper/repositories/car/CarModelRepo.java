package by.gurinovich.bamper.repositories.car;

import by.gurinovich.bamper.models.carsEntities.CarBrand;
import by.gurinovich.bamper.models.carsEntities.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelRepo extends JpaRepository<CarModel, Integer> {
    List<CarModel> findByCarBrand(CarBrand carBrand);
    void deleteByName(String name);
}
