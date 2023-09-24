package by.gurinovich.bamper.services.sparePart;

import by.gurinovich.bamper.DTO.spareParts.SparePartDTO;
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
        return sparePartRepo.findById(id).orElse(null);
    }

    @Transactional
    public boolean deleteById(Integer id){
        if (sparePartRepo.findById(id).isPresent()) {
            sparePartRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public SparePart save(SparePartCreatingRequest sparePartCreatingRequest){
        SparePartName sparePartName = sparePartCreatingRequest.getName_id() != null ?
                sparePartNameRepo.findById(sparePartCreatingRequest.getName_id()).get() : sparePartNameRepo.findByName(sparePartCreatingRequest.getName()).get();

        return sparePartName != null ? sparePartRepo.save(new SparePart(sparePartName, sparePartCreatingRequest.getNumber())) : null;
    }


    public static SparePartDTO converToDTO(SparePart sparePart){
        return new SparePartDTO(sparePart.getId(),
                SparePartNameService.convertToDTO(sparePart.getName()), sparePart.getNumber());
    }

}
