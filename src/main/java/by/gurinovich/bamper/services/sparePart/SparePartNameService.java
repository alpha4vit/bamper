package by.gurinovich.bamper.services.sparePart;

import by.gurinovich.bamper.DTO.spareParts.SparePartNameDTO;
import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.sparePartsEntities.SparePartName;
import by.gurinovich.bamper.repositories.sparePart.SparePartNameRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SparePartNameService {
    private final SparePartNameRepo sparePartNameRepo;

    public SparePartName getById(Integer name_id){
        return  sparePartNameRepo.findById(name_id).orElseThrow(() ->
                new ResourceNotFoundException("SparePartName with this ID not found"));
    }


    @Transactional
    public void deleteById(Integer sparePartNameId){
        if (sparePartNameRepo.findById(sparePartNameId).isEmpty())
            throw new ResourceNotFoundException("SparePartName with this ID not found");
        sparePartNameRepo.deleteById(sparePartNameId);
    }

    @Transactional
    public SparePartName save(SparePartNameDTO sparePartNameDTO){
        if (sparePartNameRepo.findByName(sparePartNameDTO.getName()).isPresent())
            throw new InvalidOperationException("SparePartName with this name already exists");
        return sparePartNameRepo.save(new SparePartName(sparePartNameDTO.getName()));
    }

    public List<SparePartName> getAllSparePartsName(){
        return sparePartNameRepo.findAll();
    }

}
