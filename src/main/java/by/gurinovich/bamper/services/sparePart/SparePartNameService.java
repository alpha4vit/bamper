package by.gurinovich.bamper.services.sparePart;

import by.gurinovich.bamper.DTO.spareParts.SparePartNameDTO;
import by.gurinovich.bamper.models.sparePartsEntities.SparePartName;
import by.gurinovich.bamper.repositories.sparePart.SparePartNameRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SparePartNameService {
    private final SparePartNameRepo sparePartNameRepo;

    public SparePartName getById(Integer name_id){
        return sparePartNameRepo.findById(name_id).orElse(null);
    }


    @Transactional
    public void delete(SparePartName sparePartName){
        sparePartNameRepo.delete(sparePartName);
    }

    @Transactional
    public void save(SparePartNameDTO sparePartNameDTO){
        sparePartNameRepo.save(new SparePartName(sparePartNameDTO.getName()));
    }

    public List<SparePartName> getAllSparePartsName(){
        return sparePartNameRepo.findAll();
    }

    public static SparePartNameDTO convertToDTO(SparePartName sparePartName){
        return new SparePartNameDTO(sparePartName.getId(), sparePartName.getName());
    }
}
