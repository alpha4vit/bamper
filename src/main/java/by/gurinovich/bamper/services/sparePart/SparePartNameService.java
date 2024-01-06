package by.gurinovich.bamper.services.sparePart;

import by.gurinovich.bamper.DTO.spareParts.SparePartNameDTO;
import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.sparePartsEntities.SparePartName;
import by.gurinovich.bamper.repositories.sparePart.SparePartNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SparePartNameService {
    private final SparePartNameRepository sparePartNameRepository;

    public SparePartName getById(Integer name_id){
        return  sparePartNameRepository.findById(name_id).orElseThrow(() ->
                new ResourceNotFoundException("SparePartName with this ID not found"));
    }


    @Transactional
    public void deleteById(Integer sparePartNameId){
        if (sparePartNameRepository.findById(sparePartNameId).isEmpty())
            throw new ResourceNotFoundException("SparePartName with this ID not found");
        sparePartNameRepository.deleteById(sparePartNameId);
    }

    @Transactional
    public SparePartName save(SparePartNameDTO sparePartNameDTO){
        if (sparePartNameRepository.findByName(sparePartNameDTO.getName()).isPresent())
            throw new InvalidOperationException("SparePartName with this name already exists");
        return sparePartNameRepository.save(new SparePartName(sparePartNameDTO.getName()));
    }

    public List<SparePartName> getAllSparePartsName(){
        return sparePartNameRepository.findAll();
    }

}
