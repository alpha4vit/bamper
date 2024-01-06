package by.gurinovich.bamper.repositories.car;

import by.gurinovich.bamper.models.carsEntities.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarBrandRepository extends JpaRepository<CarBrand, Integer> {
    Optional<CarBrand> findByName(String name);
}
