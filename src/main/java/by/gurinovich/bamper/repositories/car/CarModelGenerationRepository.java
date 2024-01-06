package by.gurinovich.bamper.repositories.car;

import by.gurinovich.bamper.models.carsEntities.CarModel;
import by.gurinovich.bamper.models.carsEntities.CarModelGeneration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarModelGenerationRepository extends JpaRepository<CarModelGeneration, Integer> {
    List<CarModelGeneration> findAllByCarModel(CarModel carModel);
}
