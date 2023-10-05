package by.gurinovich.bamper.services.sparePart;

import by.gurinovich.bamper.DTO.spareParts.SparePartDTO;
import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import by.gurinovich.bamper.models.sparePartsEntities.SparePartName;
import by.gurinovich.bamper.repositories.sparePart.SparePartNameRepo;
import by.gurinovich.bamper.repositories.sparePart.SparePartRepo;
import by.gurinovich.bamper.requests.SparePartCreatingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SparePartService {
    private final SparePartRepo sparePartRepo;
    private final SparePartNameRepo sparePartNameRepo;


    public List<SparePart> findAll(){
        return sparePartRepo.findAll();
    }

    public SparePart getById(Integer id){
        return sparePartRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("SparePart with this id or name not found"));
    }

    @Transactional
    public void deleteById(Integer id){
        if (sparePartRepo.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("SparePart with this id or name not found");
        }
        sparePartRepo.deleteById(id);
    }

    @Transactional
    public SparePart save(SparePartCreatingRequest sparePartCreatingRequest){
        SparePartName sparePartName = sparePartCreatingRequest.getName_id() != null ?
                sparePartNameRepo.findById(sparePartCreatingRequest.getName_id()).get() : sparePartCreatingRequest.getName() != null ? sparePartNameRepo.findByName(sparePartCreatingRequest.getName()).get() : null;
        if (sparePartName == null)
            throw new InvalidOperationException("SparePartName with this id or name not found");
        return sparePartRepo.save(new SparePart(sparePartName, sparePartCreatingRequest.getNumber()));
    }


}
