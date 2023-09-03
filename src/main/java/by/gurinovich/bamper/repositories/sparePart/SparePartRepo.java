package by.gurinovich.bamper.repositories.sparePart;

import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SparePartRepo extends JpaRepository<SparePart, Integer> {
}
