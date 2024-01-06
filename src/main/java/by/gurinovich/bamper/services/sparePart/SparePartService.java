package by.gurinovich.bamper.services.sparePart;

import by.gurinovich.bamper.exceptions.InvalidOperationException;
import by.gurinovich.bamper.exceptions.ResourceNotFoundException;
import by.gurinovich.bamper.models.sparePartsEntities.SparePart;
import by.gurinovich.bamper.models.sparePartsEntities.SparePartName;
import by.gurinovich.bamper.repositories.sparePart.SparePartNameRepository;
import by.gurinovich.bamper.repositories.sparePart.SparePartRepository;
import by.gurinovich.bamper.requests.SparePartCreatingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SparePartService {
    private final SparePartRepository sparePartRepository;
    private final SparePartNameRepository sparePartNameRepository;


    public List<SparePart> findAll(){
        return sparePartRepository.findAll();
    }

    public SparePart getById(Long id){
        return sparePartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("SparePart with this id or name not found"));
    }

    @Transactional
    public void deleteById(Long id){
        if (sparePartRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("SparePart with this id or name not found");
        }
        sparePartRepository.deleteById(id);
    }

    @Transactional
    public SparePart save(SparePartCreatingRequest sparePartCreatingRequest){
        SparePartName sparePartName = sparePartCreatingRequest.getName_id() != null ?
                sparePartNameRepository.findById(sparePartCreatingRequest.getName_id()).get() : sparePartCreatingRequest.getName() != null ? sparePartNameRepository.findByName(sparePartCreatingRequest.getName()).get() : null;
        if (sparePartName == null)
            throw new InvalidOperationException("SparePartName with this id or name not found");
        return sparePartRepository.save(new SparePart(sparePartName, sparePartCreatingRequest.getNumber()));
    }


}
