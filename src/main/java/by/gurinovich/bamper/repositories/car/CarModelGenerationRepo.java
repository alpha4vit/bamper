package by.gurinovich.bamper.repositories.car;

import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.models.carsEntities.CarModelGeneration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface CarModelGenerationRepo extends JpaRepository<CarModelGeneration, Integer> {
    List<CarModelGeneration> findAllByCarModel(CarModel carModel);
}
