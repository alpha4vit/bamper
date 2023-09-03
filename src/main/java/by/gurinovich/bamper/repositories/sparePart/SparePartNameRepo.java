package by.gurinovich.bamper.repositories.sparePart;

import by.gurinovich.bamper.models.sparePartsEntities.SparePartName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SparePartNameRepo extends JpaRepository<SparePartName, Integer> { Optional<SparePartName> findByName(String name);
}
